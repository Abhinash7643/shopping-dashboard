package com.abhinash.shoppingdashboard.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Random;


/**
 * Generate unique IDs using the Twitter Snowflake algorithm (see https://github.com/twitter/snowflake). Snowflake IDs
 * are 64 bit positive longs composed of:
 * - 41 bits time stamp
 * - 10 bits machine id
 * - 12 bits sequence number
 *
 *
 * @author Sebastian Schaffert (sschaffert@apache.org)
 */
public class SnowflakeIdGenerator {

    private static Logger log = LoggerFactory.getLogger(SnowflakeIdGenerator.class);


    private final long datacenterIdBits = 10L;
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;

    private final long datacenterIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private final long twepoch = 1451586600000L; // 01/01/2016 our base epoch
    private long datacenterId;

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    private static SnowflakeIdGenerator instance = null;
    public static SnowflakeIdGenerator getInstance() {
        if ( null != instance ) return instance;
        synchronized (SnowflakeIdGenerator.class.getName()) {
            if ( null != instance ) return instance;
            instance = new SnowflakeIdGenerator(0);
        }
        return instance;
    }

    public SnowflakeIdGenerator(long datacenterId)  {
        if(datacenterId == 0) {
            try {
                this.datacenterId = getDatacenterId();
            } catch (SocketException | UnknownHostException | NullPointerException e) {
                log.warn("SNOWFLAKE: could not determine machine address; using random datacenter ID");
                Random rnd = new Random();
                this.datacenterId = rnd.nextInt((int)maxDatacenterId) + 1;
            }
        } else {
            this.datacenterId = datacenterId;
        }

        if (this.datacenterId > maxDatacenterId || datacenterId < 0){
            log.warn("SNOWFLAKE: datacenterId > maxDatacenterId; using random datacenter ID");
            Random rnd = new Random();
            this.datacenterId = rnd.nextInt((int)maxDatacenterId) + 1;
        }
        log.info("SNOWFLAKE: initialised with datacenter ID {}", this.datacenterId);
    }

    protected long tilNextMillis(long lastTimestamp){
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    protected long getDatacenterId() throws SocketException, UnknownHostException {
        NetworkInterface network = null;
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface nint = en.nextElement();
            if (!nint.isLoopback() && nint.getHardwareAddress() != null) {
                network = nint;
                break;
            }
        }

        byte[] mac = network.getHardwareAddress();

        Random rnd = new Random();
        byte rndByte = (byte)(rnd.nextInt() & 0x000000FF);

        // take the last byte of the MAC address and a random byte as datacenter ID
        long id = ((0x000000FF & (long)mac[mac.length-1]) | (0x0000FF00 & (((long)rndByte)<<8)))>>6;


        return id;
    }


    /**
     * Return the next unique id for the type with the given name using the generator's id generation strategy.
     *
     * @return
     */
    public synchronized long getId() {
        long timestamp = System.currentTimeMillis();
        if(timestamp<lastTimestamp) {
            log.warn("Clock moved backwards. Refusing to generate id for {} milliseconds.",(lastTimestamp - timestamp));
            try {
                Thread.sleep((lastTimestamp - timestamp));
            } catch (InterruptedException e) {
            }
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | sequence;

        if(id < 0) {
            log.warn("ID is smaller than 0: {}",id);
        }
        return id;
    }

    public String getIdStr(String prefix) {
        Long id = getId();
        return (null == prefix) ? id.toString() : prefix + id;
    }

    public String getIdHex(String prefix) {
        String id = Long.toHexString(getId()).toUpperCase();
        return (null == prefix) ? id : prefix + id;
    }

}

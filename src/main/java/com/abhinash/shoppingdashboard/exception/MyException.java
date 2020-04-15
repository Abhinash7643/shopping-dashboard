package com.abhinash.shoppingdashboard.exception;

public class MyException extends RuntimeException {

    int code = -1;

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(int code, String message) {
        super(message);
        this.code = code;
    }

}

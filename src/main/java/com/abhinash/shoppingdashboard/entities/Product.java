package com.abhinash.shoppingdashboard.entities;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", length = 1000)
    private String productName;

    @Column(name = "product_description", length = 5000)
    private String productDescription;

    @Column(name = "product_image_url", length = 5000)
    private String productImageUrl;

    @Column(name = "product_category", length = 5000)
    private String productCategory;

    @Column(name = "product_seller", length = 1000)
    private String productSeller;

    @Column(name = "product_price")
    private String productPrice;

    @CreationTimestamp
    @Column(name = "created_on")
    private Date createdOn;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(String productSeller) {
        this.productSeller = productSeller;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
//    $key: string;

//    productCategory: string;
//    productPrice: number;
//    productDescription: string;
//    productImageUrl: string;
//    productAdded: number;
//    productQuatity: number;
//    ratings: number;
//    favourite: boolean;
//    productSeller: string;
}

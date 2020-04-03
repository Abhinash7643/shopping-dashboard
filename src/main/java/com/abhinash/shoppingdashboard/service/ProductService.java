package com.abhinash.shoppingdashboard.service;

import com.abhinash.shoppingdashboard.entities.Product;
import com.abhinash.shoppingdashboard.repository.ProductRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepos productRepos;

    public Product saveUpdateProduct(Product product){
        return productRepos.save(product);
    }

    public List<Product> saveAllProduct(List<Product> products){
        return productRepos.saveAll(products);
    }

    public List<Product> getAllProduct(){
        return productRepos.findAll();
    }
}

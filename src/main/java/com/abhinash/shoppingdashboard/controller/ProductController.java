package com.abhinash.shoppingdashboard.controller;

import com.abhinash.shoppingdashboard.entities.Product;
import com.abhinash.shoppingdashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/v1/greet")
    public String greet(){
        return "greeting";
    }

    public Product saveUpdateProduct(@RequestBody Product product){
        return productService.saveUpdateProduct(product);
    }

    @PostMapping(value = "/v1/products/save")
    public List<Product> saveAllProduct(@RequestBody List<Product> products){
        return productService.saveAllProduct(products);
    }

    @GetMapping(value = "/v1/products")
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }

}

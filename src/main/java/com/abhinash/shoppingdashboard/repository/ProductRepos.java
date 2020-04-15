package com.abhinash.shoppingdashboard.repository;

import com.abhinash.shoppingdashboard.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepos extends MongoRepository<Product, Long> {
}

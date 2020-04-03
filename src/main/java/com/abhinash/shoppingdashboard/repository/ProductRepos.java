package com.abhinash.shoppingdashboard.repository;

import com.abhinash.shoppingdashboard.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepos extends JpaRepository<Product, Long> {
}

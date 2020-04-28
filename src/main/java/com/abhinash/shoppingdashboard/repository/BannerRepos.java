package com.abhinash.shoppingdashboard.repository;

import com.abhinash.shoppingdashboard.entities.Banner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BannerRepos extends MongoRepository<Banner, String> {
}

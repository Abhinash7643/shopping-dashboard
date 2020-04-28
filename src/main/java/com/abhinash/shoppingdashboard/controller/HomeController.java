package com.abhinash.shoppingdashboard.controller;

import com.abhinash.shoppingdashboard.entities.Banner;
import com.abhinash.shoppingdashboard.repository.BannerRepos;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "countries")
public class HomeController extends AbstractBaseController{

    @Autowired
    private BannerRepos bannerRepos;

    @PostMapping(value = "/v1/banners")
    public ResponseEntity<Banner> addBanner(@RequestBody Banner banner){
        return ok(bannerRepos.save(banner));
    }

    @GetMapping(value = "/v1/banners")
    public ResponseEntity<List<Banner>> getAllBanners(){
        return ok(bannerRepos.findAll());
    }





}

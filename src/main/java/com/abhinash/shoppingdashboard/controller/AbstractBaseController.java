package com.abhinash.shoppingdashboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AbstractBaseController {

    protected ResponseEntity<HttpStatus> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> ok(T message) {
        return new ResponseEntity<T>(message, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> unauthorized() {
        return new ResponseEntity<T>(HttpStatus.UNAUTHORIZED);
    }

    protected <T> ResponseEntity<T> notModified() {
        return  new ResponseEntity<T>(HttpStatus.NOT_MODIFIED);
    }

    protected <T> ResponseEntity<T> created(T message) {
        return new ResponseEntity<T>(message, HttpStatus.CREATED);
    }
}

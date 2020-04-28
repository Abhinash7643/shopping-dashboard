package com.abhinash.shoppingdashboard.exception;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    private int code;

    private String message;

    private Map<String, String> errors;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.errors = new HashMap<>();
    }

    public void addError(String field, String errorMessage) {
        errors.put(field, errorMessage);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}

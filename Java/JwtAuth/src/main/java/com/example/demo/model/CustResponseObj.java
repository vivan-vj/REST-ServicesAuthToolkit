package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustResponseObj {
    private String message;
    private String error;
    private Customer customer;

    public CustResponseObj() {
    }

    public CustResponseObj(String message, String error, Customer customer) {
        this.message = message;
        this.error = error;
        this.customer = customer;
    }
}

package com.example.demo.model;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RequestObj {
    private String token;
    private String username;
    private String password;
    private String domain;
    private String groupCode;
}

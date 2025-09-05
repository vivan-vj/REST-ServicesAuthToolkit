package com.example.demo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseObj {
    private String token;
    private String message;
    private String error;
    private String expiresIn;
}

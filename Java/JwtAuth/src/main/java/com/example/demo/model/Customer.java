package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String city;
    private String email;
    private String phone;
}

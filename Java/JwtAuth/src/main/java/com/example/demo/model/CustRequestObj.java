package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustRequestObj {
    private String name;
    private String city;
    private String email;
    private String phone;
    private Long id;
}

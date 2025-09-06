package com.example.demo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * The type Customer.
 *
 * @author Vivek Jadhav
 */
@Data
@Component
public class Customer {
    private Long id;
    private String name;
    private String city;
    private String email;
    private String phone;

    /**
     * Instantiates a new Customer.
     */
    public Customer() {
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id    the id
     * @param name  the name
     * @param city  the city
     * @param email the email
     * @param phone the phone
     */
    public Customer(Long id, String name, String city, String email, String phone) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }
}

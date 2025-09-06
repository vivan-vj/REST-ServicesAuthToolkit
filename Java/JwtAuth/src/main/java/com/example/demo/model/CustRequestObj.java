package com.example.demo.model;

import lombok.Data;

/**
 * The type Cust request obj.
 *
 * @author Vivek Jadhav
 */
@Data
public class CustRequestObj {
    private String name;
    private String city;
    private String email;
    private String phone;
    private Long id;

    /**
     * Instantiates a new Cust request obj.
     */
    public CustRequestObj() {
    }

    /**
     * Instantiates a new Cust request obj.
     *
     * @param name  the name
     * @param city  the city
     * @param email the email
     * @param phone the phone
     * @param id    the id
     */
    public CustRequestObj(String name, String city, String email, String phone, Long id) {
        this.name = name;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.id = id;
    }
}

package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * The type Cust response obj.
 *
 * @author Vivek Jadhav
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustResponseObj {
    private String message;
    private String error;
    private Customer customer;

    /**
     * Instantiates a new Cust response obj.
     */
    public CustResponseObj() {
    }

    /**
     * Instantiates a new Cust response obj.
     *
     * @param message  the message
     * @param error    the error
     * @param customer the customer
     */
    public CustResponseObj(String message, String error, Customer customer) {
        this.message = message;
        this.error = error;
        this.customer = customer;
    }
}

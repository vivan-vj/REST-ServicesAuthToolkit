package com.example.demo.service;


import com.example.demo.model.Customer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Customer service.
 *
 * @author Vivek Jadhav
 */
@Slf4j
@Component
public class CustomerService {

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        log.info("CustomerService initialized");
    }

    /**
     * Gets customer by id.
     *
     * @param id the id
     * @return the customer by id
     */
    public Customer getCustomerById(Long id) {
        // In a real application, this would fetch data from a database
        return new Customer(id, "John Doe", "New York", "", "123-456-7890");
    }

    /**
     * Create customer boolean.
     *
     * @param name  the name
     * @param city  the city
     * @param email the email
     * @param phone the phone
     * @return the boolean
     */
    public Boolean createCustomer(String name, String city, String email, String phone) {
        if (name == null || city == null || email == null || phone == null) {
            log.error("Customer data is incomplete");
            return false;
        }
        // In a real application, this would save data to a database
        return true;
    }

    /**
     * Delete customer boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public Boolean deleteCustomer(Long id) {
        // In a real application, this would delete data from a database
        return true;
    }

    /**
     * Update customer boolean.
     *
     * @param id       the id
     * @param customer the customer
     * @return the boolean
     */
    public Boolean updateCustomer(Long id, Customer customer) {
        if (customer.getName() == null || customer.getCity() == null
                || customer.getEmail() == null || customer.getPhone() == null) {
            log.error("Customer data is incomplete");
            return false;
        }
        // In a real application, this would update data in a database
        customer.setId(id);
        return true;
    }
}

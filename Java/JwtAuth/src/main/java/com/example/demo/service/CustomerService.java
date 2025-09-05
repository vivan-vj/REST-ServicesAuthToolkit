package com.example.demo.service;


import com.example.demo.model.Customer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerService {

    @PostConstruct
    public void init() {
        log.info("CustomerService initialized");
    }

    public Customer getCustomerById(Long id) {
        // In a real application, this would fetch data from a database
        return new Customer(id, "John Doe", "New York", "", "123-456-7890");
    }

    public Boolean createCustomer(String name, String city, String email, String phone) {
        if (name == null || city == null || email == null || phone == null) {
            log.error("Customer data is incomplete");
            return false;
        }
        // In a real application, this would save data to a database
        return true;
    }

    public Boolean deleteCustomer(Long id) {
        // In a real application, this would delete data from a database
        return true;
    }

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

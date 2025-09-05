package com.example.demo.controllers;

import com.example.demo.model.CustRequestObj;
import com.example.demo.model.CustResponseObj;
import com.example.demo.service.CustomerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Customer service rest controller.
 *
 * @author Vivek Jadhav
 */
@Slf4j
@RestController
@RequestMapping("/rest/customer")
public class CustomerServiceRestController {

    private final CustomerService customerService;

    /**
     * Instantiates a new Customer service rest controller.
     *
     * @param customerService the customer service
     */
    public CustomerServiceRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        log.info("CustomerServiceRestController initialized");
    }

    /**
     * Create customer response entity.
     *
     * @param custRequestObj the cust request obj
     * @return the response entity
     */
    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustResponseObj> createCustomer(@RequestBody CustRequestObj custRequestObj) {
        CustResponseObj custResponseObj = new CustResponseObj();
        try {
            boolean isCreated = customerService.createCustomer(custRequestObj.getName(), custRequestObj.getCity(),
                    custRequestObj.getEmail(), custRequestObj.getPhone());
            if (isCreated) {
                custResponseObj.setMessage("Customer created successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(custResponseObj);
            } else {
                custResponseObj.setError("Failed to create customer");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(custResponseObj);
            }
        } catch (Exception e) {
            log.error("Unexpected error while creating customer: {}", e.getMessage());
            custResponseObj.setError("Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(custResponseObj);
        }
    }

    /**
     * Delete customer by id response entity.
     *
     * @param custRequestObj the cust request obj
     * @return the response entity
     */
    @PostMapping(path = "/deleteById", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustResponseObj> deleteCustomerById(@RequestBody CustRequestObj custRequestObj) {
        CustResponseObj custResponseObj = new CustResponseObj();
        try {
            boolean isDeleted = customerService.deleteCustomer(custRequestObj.getId());
            if (isDeleted) {
                custResponseObj.setMessage("Customer deleted successfully");
                return ResponseEntity.ok(custResponseObj);
            } else {
                custResponseObj.setError("Failed to delete customer");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(custResponseObj);
            }
        } catch (Exception e) {
            log.error("Unexpected error while deleting customer: {}", e.getMessage());
            custResponseObj.setError("Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(custResponseObj);
        }
    }

}

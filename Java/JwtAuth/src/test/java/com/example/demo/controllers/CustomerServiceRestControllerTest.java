package com.example.demo.controllers;

import com.example.demo.model.CustRequestObj;
import com.example.demo.model.CustResponseObj;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Customer service rest controller test.
 *
 * @author Vivek Jadhav
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceRestControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerServiceRestController controller;

    /**
     * Create customer returns created status when customer is created successfully.
     */
    @Test
    void createCustomerReturnsCreatedStatusWhenCustomerIsCreatedSuccessfully() {
        CustRequestObj request = new CustRequestObj("John Doe", "New York", "john.doe@example.com", "1234567890", 0L);
        CustResponseObj expectedResponse = new CustResponseObj();
        expectedResponse.setMessage("Customer created successfully");

        Mockito.when(customerService.createCustomer(request.getName(), request.getCity(), request.getEmail(), request.getPhone()))
                .thenReturn(true);

        ResponseEntity<CustResponseObj> response = controller.createCustomer(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
    }

    /**
     * Create customer returns internal server error when customer creation fails.
     */
    @Test
    void createCustomerReturnsInternalServerErrorWhenCustomerCreationFails() {
        CustRequestObj request = new CustRequestObj("John Doe", "New York", "john.doe@example.com", "1234567890", 0L);
        CustResponseObj expectedResponse = new CustResponseObj();
        expectedResponse.setError("Failed to create customer");

        Mockito.when(customerService.createCustomer(request.getName(), request.getCity(), request.getEmail(), request.getPhone()))
                .thenReturn(false);

        ResponseEntity<CustResponseObj> response = controller.createCustomer(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }

    /**
     * Create customer returns internal server error when exception occurs.
     */
    @Test
    void createCustomerReturnsInternalServerErrorWhenExceptionOccurs() {
        CustRequestObj request = new CustRequestObj("John Doe", "New York", "john.doe@example.com", "1234567890", 0L);
        CustResponseObj expectedResponse = new CustResponseObj();
        expectedResponse.setError("Internal server error");

        Mockito.when(customerService.createCustomer(request.getName(), request.getCity(), request.getEmail(), request.getPhone()))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<CustResponseObj> response = controller.createCustomer(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }

    /**
     * Delete customer by id returns ok status when customer is deleted successfully.
     */
    @Test
    void deleteCustomerByIdReturnsOkStatusWhenCustomerIsDeletedSuccessfully() {
        CustRequestObj request = new CustRequestObj();
        request.setId(1L);
        CustResponseObj expectedResponse = new CustResponseObj();
        expectedResponse.setMessage("Customer deleted successfully");

        Mockito.when(customerService.deleteCustomer(request.getId())).thenReturn(true);

        ResponseEntity<CustResponseObj> response = controller.deleteCustomerById(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
    }

    /**
     * Delete customer by id returns internal server error when customer deletion fails.
     */
    @Test
    void deleteCustomerByIdReturnsInternalServerErrorWhenCustomerDeletionFails() {
        CustRequestObj request = new CustRequestObj();
        request.setId(1L);
        CustResponseObj expectedResponse = new CustResponseObj();
        expectedResponse.setError("Failed to delete customer");

        Mockito.when(customerService.deleteCustomer(request.getId())).thenReturn(false);

        ResponseEntity<CustResponseObj> response = controller.deleteCustomerById(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }

    /**
     * Delete customer by id returns internal server error when exception occurs.
     */
    @Test
    void deleteCustomerByIdReturnsInternalServerErrorWhenExceptionOccurs() {
        CustRequestObj request = new CustRequestObj();
        request.setId(1L);
        CustResponseObj expectedResponse = new CustResponseObj();
        expectedResponse.setError("Internal server error");

        Mockito.when(customerService.deleteCustomer(request.getId())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<CustResponseObj> response = controller.deleteCustomerById(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }
}
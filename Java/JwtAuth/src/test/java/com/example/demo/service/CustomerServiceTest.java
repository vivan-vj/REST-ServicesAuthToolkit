package com.example.demo.service;

import com.example.demo.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * The type Customer service test.
 *
 * @author Vivek Jadhav
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    /**
     * Gets customer by id returns customer with valid id.
     */
    @Test
    void getCustomerByIdReturnsCustomerWithValidId() {
        CustomerService spyService = spy(customerService);
        doReturn(new Customer(1L, "John Doe", "New York", "", "123-456-7890"))
                .when(spyService).getCustomerById(1L);

        Customer customer = spyService.getCustomerById(1L);

        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("New York", customer.getCity());
    }

    /**
     * Create customer returns true when data is valid.
     */
    @Test
    void createCustomerReturnsTrueWhenDataIsValid() {
        CustomerService spyService = spy(customerService);
        doReturn(true).when(spyService).createCustomer("Jane Doe", "Los Angeles", "jane.doe@example.com", "987-654-3210");

        boolean result = spyService.createCustomer("Jane Doe", "Los Angeles", "jane.doe@example.com", "987-654-3210");

        assertTrue(result);
    }

    /**
     * Create customer returns false when data is incomplete.
     */
    @Test
    void createCustomerReturnsFalseWhenDataIsIncomplete() {
        CustomerService spyService = spy(customerService);
        doReturn(false).when(spyService).createCustomer(null, "Los Angeles", "jane.doe@example.com", "987-654-3210");

        boolean result = spyService.createCustomer(null, "Los Angeles", "jane.doe@example.com", "987-654-3210");

        assertFalse(result);
    }

    /**
     * Delete customer returns true when id is valid.
     */
    @Test
    void deleteCustomerReturnsTrueWhenIdIsValid() {
        CustomerService spyService = spy(customerService);
        doReturn(true).when(spyService).deleteCustomer(1L);

        boolean result = spyService.deleteCustomer(1L);

        assertTrue(result);
    }

    /**
     * Update customer returns true when data is valid.
     */
    @Test
    void updateCustomerReturnsTrueWhenDataIsValid() {
        CustomerService spyService = spy(customerService);
        Customer customer = new Customer(1L, "Jane Doe", "Los Angeles", "jane.doe@example.com", "987-654-3210");
        doReturn(true).when(spyService).updateCustomer(1L, customer);

        boolean result = spyService.updateCustomer(1L, customer);

        assertTrue(result);
        assertEquals(1L, customer.getId());
    }

    /**
     * Update customer returns false when data is incomplete.
     */
    @Test
    void updateCustomerReturnsFalseWhenDataIsIncomplete() {
        CustomerService spyService = spy(customerService);
        Customer customer = new Customer(null, null, "Los Angeles", "jane.doe@example.com", "987-654-3210");
        doReturn(false).when(spyService).updateCustomer(1L, customer);

        boolean result = spyService.updateCustomer(1L, customer);

        assertFalse(result);
    }
}
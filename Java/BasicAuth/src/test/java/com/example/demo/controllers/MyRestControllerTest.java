package com.example.demo.controllers;

import com.example.demo.model.RequestObj;
import com.example.demo.model.ResponseObj;
import com.example.demo.util.RestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MyRestControllerTest {

    @Test
    void submit_shouldReturnSuccessResponse() {
        MyRestController controller = new MyRestController();
        RequestObj mockRequestObj = Mockito.mock(RequestObj.class);

        ResponseEntity<ResponseObj> response = controller.submit(mockRequestObj);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(RestUtil.SUCCESS, response.getBody().getStatus());
        assertEquals("Submitted DATA ", response.getBody().getMessage());
    }
}
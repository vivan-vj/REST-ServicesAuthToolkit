package com.example.demo.controllers;

import com.example.demo.model.RequestObj;
import com.example.demo.model.ResponseObj;
import com.example.demo.service.JwtServiceUtil;
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
 * The type Jwt service rest controller test.
 *
 * @author Vivek Jadhav
 */
@ExtendWith(MockitoExtension.class)
class JwtServiceRestControllerTest {

    @Mock
    private JwtServiceUtil jwtServiceUtil;

    @InjectMocks
    private JwtServiceRestController controller;

    /**
     * Health check returns service status message.
     */
    @Test
    void healthCheckReturnsServiceStatusMessage() {
        String response = controller.healthCheck();
        assertEquals("JWT Rest Services are up and running", response);
    }

    /**
     * Gets jwt token returns token when credentials are valid.
     */
    @Test
    void getJwtTokenReturnsTokenWhenCredentialsAreValid() {
        RequestObj request = getRequestObj();
        ResponseObj expectedResponse = new ResponseObj();
        expectedResponse.setToken("mockToken");
        expectedResponse.setExpiresIn("3600");
        expectedResponse.setMessage("Token generated successfully");

        Mockito.when(jwtServiceUtil.generateToken(request.getUsername(), request.getPassword(), request.getDomain(), request.getGroupCode()))
                .thenReturn("mockToken");
        Mockito.when(jwtServiceUtil.getExpirationTime()).thenReturn(3600L);

        ResponseEntity<ResponseObj> response = controller.getJwtToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getToken(), response.getBody().getToken());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
    }

    /**
     * Gets jwt token returns unauthorized when credentials are invalid.
     */
    @Test
    void getJwtTokenReturnsUnauthorizedWhenCredentialsAreInvalid() {
        RequestObj request = getRequestObj();
        ResponseObj expectedResponse = new ResponseObj();
        expectedResponse.setError("Invalid credentials");

        Mockito.when(jwtServiceUtil.generateToken(request.getUsername(), request.getPassword(), request.getDomain(), request.getGroupCode()))
                .thenThrow(new IllegalArgumentException("Invalid credentials"));

        ResponseEntity<ResponseObj> response = controller.getJwtToken(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }

    /**
     * Gets jwt token returns internal server error when unexpected error occurs.
     */
    @Test
    void getJwtTokenReturnsInternalServerErrorWhenUnexpectedErrorOccurs() {
        RequestObj request = getRequestObj();
        ResponseObj expectedResponse = new ResponseObj();
        expectedResponse.setError("Internal server error");

        Mockito.when(jwtServiceUtil.generateToken(request.getUsername(), request.getPassword(), request.getDomain(), request.getGroupCode()))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<ResponseObj> response = controller.getJwtToken(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }

    /**
     * Validate token returns valid message when token is valid.
     */
    @Test
    void validateTokenReturnsValidMessageWhenTokenIsValid() {
        RequestObj request = new RequestObj();
        request.setToken("validToken");
        ResponseObj expectedResponse = new ResponseObj();
        expectedResponse.setMessage("Token is valid");

        Mockito.when(jwtServiceUtil.validateToken(request.getToken())).thenReturn(true);

        ResponseEntity<ResponseObj> response = controller.validateToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
    }

    /**
     * Validate token returns unauthorized when token is invalid.
     */
    @Test
    void validateTokenReturnsUnauthorizedWhenTokenIsInvalid() {
        RequestObj request = new RequestObj();
        request.setToken("invalidToken");
        ResponseObj expectedResponse = new ResponseObj();
        expectedResponse.setError("Invalid token");

        Mockito.when(jwtServiceUtil.validateToken(request.getToken())).thenReturn(false);

        ResponseEntity<ResponseObj> response = controller.validateToken(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }

    /**
     * Validate token returns internal server error when unexpected error occurs.
     */
    @Test
    void validateTokenReturnsInternalServerErrorWhenUnexpectedErrorOccurs() {
        RequestObj request = new RequestObj();
        request.setToken("anyToken");
        ResponseObj expectedResponse = new ResponseObj();
        expectedResponse.setError("Internal server error");

        Mockito.when(jwtServiceUtil.validateToken(request.getToken())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<ResponseObj> response = controller.validateToken(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.getError(), response.getBody().getError());
    }

    private RequestObj getRequestObj() {
        RequestObj requestObj = new RequestObj();
        requestObj.setUsername("user");
        requestObj.setPassword("password");
        requestObj.setDomain("domain");
        requestObj.setGroupCode("groupCode");
        return requestObj;
    }
}
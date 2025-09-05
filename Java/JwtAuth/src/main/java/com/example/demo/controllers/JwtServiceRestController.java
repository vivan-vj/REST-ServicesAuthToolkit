package com.example.demo.controllers;


import com.example.demo.model.RequestObj;
import com.example.demo.model.ResponseObj;
import com.example.demo.service.JwtServiceUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/rest/jwt")
public class JwtServiceRestController {

    private final JwtServiceUtil jwtServiceUtil;

    public JwtServiceRestController(JwtServiceUtil jwtServiceUtil) {
        this.jwtServiceUtil = jwtServiceUtil;
    }

    @PostConstruct
    void init() {
        log.info("JwtResController initialized");
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "JWT Rest Services are up and running";
    }

    @PostMapping(path = "/fetchToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseObj> getJwtToken(@RequestBody RequestObj requestObj) {
        ResponseObj responseObj = new ResponseObj();
        try {
            String token = jwtServiceUtil.generateToken(requestObj.getUsername(),
                    requestObj.getPassword(), requestObj.getDomain(), requestObj.getGroupCode());
            String expiresIn = jwtServiceUtil.getExpirationTime() + "";
            responseObj.setToken(token);
            responseObj.setExpiresIn(expiresIn);
            responseObj.setMessage("Token generated successfully");
            return ResponseEntity.ok(responseObj);
        } catch (IllegalArgumentException e) {
            log.error("Error generating token: {}", e.getMessage());
            responseObj.setError("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObj);
        } catch (Exception e) {
            log.error("Unexpected error while fetching token : {}", e.getMessage());
            responseObj.setError("Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PostMapping(path = "/validateToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseObj> validateToken(@RequestBody RequestObj requestObj) {
        ResponseObj responseObj = new ResponseObj();
        try {
            boolean isValid = jwtServiceUtil.validateToken(requestObj.getToken());
            if (isValid) {
                responseObj.setMessage("Token is valid");
                return ResponseEntity.ok(responseObj);
            } else {
                responseObj.setError("Invalid token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObj);
            }
        } catch (Exception e) {
            log.error("Unexpected error while validating token : {}", e.getMessage());
            responseObj.setError("Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }



}

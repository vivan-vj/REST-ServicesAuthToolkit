package com.example.demo.controllers;


import com.example.demo.model.RequestObj;
import com.example.demo.model.ResponseObj;
import com.example.demo.service.JwtServiceUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Jwt service rest controller.
 *
 * @author Vivek Jadhav
 */
@Slf4j
@RestController
@RequestMapping("/rest/jwt")
public class JwtServiceRestController {

    private final JwtServiceUtil jwtServiceUtil;

    /**
     * Instantiates a new Jwt service rest controller.
     *
     * @param jwtServiceUtil the jwt service util
     */
    public JwtServiceRestController(JwtServiceUtil jwtServiceUtil) {
        this.jwtServiceUtil = jwtServiceUtil;
    }

    /**
     * Init.
     */
    @PostConstruct
    void init() {
        log.info("JwtResController initialized");
    }

    /**
     * Health check string.
     *
     * @return the string
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "JWT Rest Services are up and running";
    }

    /**
     * Gets jwt token.
     *
     * @param requestObj the request obj
     * @return the jwt token
     */
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

    /**
     * Validate token response entity.
     *
     * @param requestObj the request obj
     * @return the response entity
     */
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

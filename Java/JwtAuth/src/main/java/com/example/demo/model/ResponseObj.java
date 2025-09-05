package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * The type Response obj.
 *
 * @author Vivek Jadhav
 */
@Data
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObj {
    private String token;
    private String message;
    private String error;
    private String expiresIn;
}

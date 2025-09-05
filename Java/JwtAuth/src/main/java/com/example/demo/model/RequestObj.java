package com.example.demo.model;


import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * The type Request obj.
 *
 * @author Vivek Jadhav
 */
@Data
@Component
public class RequestObj {
    private String token;
    private String username;
    private String password;
    private String domain;
    private String groupCode;

    /**
     * Instantiates a new Request obj.
     */
    public RequestObj() {
    }

    /**
     * Instantiates a new Request obj.
     *
     * @param token     the token
     * @param username  the username
     * @param password  the password
     * @param domain    the domain
     * @param groupCode the group code
     */
    public RequestObj(String token, String username, String password, String domain, String groupCode) {
        this.token = token;
        this.username = username;
        this.password = password;
        this.domain = domain;
        this.groupCode = groupCode;
    }

}

package com.social_network_Abbey.response;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private String expiresIn;



}
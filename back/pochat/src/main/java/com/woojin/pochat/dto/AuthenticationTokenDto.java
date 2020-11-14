package com.woojin.pochat.dto;

import lombok.Data;

@Data
public class AuthenticationTokenDto {
    private String username;
    private String token;

    public AuthenticationTokenDto(String username, String token) {
        this.username = username;
        this.token = token;
    }
}

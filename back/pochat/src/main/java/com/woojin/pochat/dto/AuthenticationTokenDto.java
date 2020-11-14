package com.woojin.pochat.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class AuthenticationTokenDto {
    private String username;
    private Collection authorities;
    private String token;

    public AuthenticationTokenDto(String username, Collection collection, String token) {
        this.username = username;
        this.authorities = collection;
        this.token = token;
    }
}

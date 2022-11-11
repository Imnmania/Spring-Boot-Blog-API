package me.niloybiswas.spblog.security;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}

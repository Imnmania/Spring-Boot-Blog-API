package me.niloybiswas.spblog.dto.security;

import lombok.Data;

@Data
public class JwtAuthRequestDTO {
    private String username;
    private String password;
}

package me.niloybiswas.spblog.dto.user;

import lombok.Data;
import me.niloybiswas.spblog.entitiy.Role;

import java.util.Set;

@Data
public class UserResponseDTO {
    private Long id;

    private String name;

    private String email;

    private String about;

    private Set<Role> roles;
}

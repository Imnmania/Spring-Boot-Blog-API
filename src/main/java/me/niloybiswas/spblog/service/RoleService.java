package me.niloybiswas.spblog.service;

import me.niloybiswas.spblog.dto.role.RoleDTO;
import me.niloybiswas.spblog.dto.role.RoleToUserDTO;
import org.springframework.stereotype.Service;

public interface RoleService {

    RoleDTO getRoleById(Long roleId);
    RoleDTO addNewRole(RoleDTO roleDTO);
    void addRoleToUser(RoleToUserDTO roleToUserDTO);
}

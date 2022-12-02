package me.niloybiswas.spblog.controller;

import lombok.AllArgsConstructor;
import me.niloybiswas.spblog.dto.role.RoleDTO;
import me.niloybiswas.spblog.dto.role.RoleToUserDTO;
import me.niloybiswas.spblog.entitiy.Role;
import me.niloybiswas.spblog.repository.RoleRepo;
import me.niloybiswas.spblog.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/role")
@AllArgsConstructor
public class RoleController {

    @Autowired
    private final RoleRepo roleRepo;
    @Autowired
    private final RoleService roleService;

    @Autowired
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addNewRole")
    public ResponseEntity<?> addNewRole(@RequestBody RoleDTO roleDTO) {
        Map<String, String> res = new HashMap<>();
        Role checkRole = roleRepo.findByName(roleDTO.getName());

        if (checkRole != null) {
            res.put("message", "this role already exists!");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        RoleDTO newRole = roleService.addNewRole(roleDTO);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL')")
    @GetMapping("/getRoles")
    public ResponseEntity<Collection<RoleDTO>> getRoles() {
        Collection<Role> roles = roleRepo.findAll();
        Collection<RoleDTO> roleDTOS = roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(roleDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserDTO roleToUserDTO) {
        roleService.addRoleToUser(roleToUserDTO);
        Map<String, String> res = new HashMap<>();
        res.put("message", "Successfully added!");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}


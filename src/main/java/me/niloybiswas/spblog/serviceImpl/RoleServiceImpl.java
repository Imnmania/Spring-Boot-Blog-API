package me.niloybiswas.spblog.serviceImpl;

import lombok.AllArgsConstructor;
import me.niloybiswas.spblog.dto.role.RoleDTO;
import me.niloybiswas.spblog.dto.role.RoleToUserDTO;
import me.niloybiswas.spblog.entitiy.Role;
import me.niloybiswas.spblog.entitiy.User;
import me.niloybiswas.spblog.exception.ResourceNotFoundException;
import me.niloybiswas.spblog.repository.RoleRepo;
import me.niloybiswas.spblog.repository.UserRepo;
import me.niloybiswas.spblog.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Autowired
    private final RoleRepo roleRepo;

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public RoleDTO getRoleById(Long roleId) {
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO addNewRole(RoleDTO roleDTO) {
        Role newRole = roleRepo.save(modelMapper.map(roleDTO, Role.class));
        return modelMapper.map(newRole, RoleDTO.class);
    }

    @Override
    public void addRoleToUser(RoleToUserDTO roleToUserDTO) {
        User user = this.userRepo.findByEmail(roleToUserDTO.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", roleToUserDTO.getUserName()));
        Role role = this.roleRepo.findByName(roleToUserDTO.getRoleName());
        user.getRoles().add(role);
        this.userRepo.save(user);
    }
}

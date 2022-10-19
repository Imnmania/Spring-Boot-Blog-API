package me.niloybiswas.spblog.service;

import java.util.List;

import me.niloybiswas.spblog.dto.user.UserDTO;

public interface UserService {

	// create
	UserDTO createUser(UserDTO userDTO);

	// update
	UserDTO updateUser(UserDTO userDTO, Long userId);

	// get by id
	UserDTO getUserById(Long userId);

	// get all
	List<UserDTO> getAllUsers();

	// delete
	void deleteUser(Long userId);

}

package me.niloybiswas.spblog.service;

import java.math.BigInteger;
import java.util.List;

import me.niloybiswas.spblog.dto.UserDTO;

public interface UserService {

	// create
	UserDTO createUser(UserDTO userDTO);

	// update
	UserDTO updateUser(UserDTO userDTO, BigInteger userId);

	// get by id
	UserDTO getUserById(BigInteger userId);

	// get all
	List<UserDTO> getAllUsers();

	// delete
	void deleteUser(BigInteger userId);

}

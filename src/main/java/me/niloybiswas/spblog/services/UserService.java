package me.niloybiswas.spblog.services;

import java.math.BigInteger;
import java.util.List;

import me.niloybiswas.spblog.payloads.UserDTO;

public interface UserService {
	
	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(UserDTO userDTO, BigInteger userId);
	
	UserDTO getUserById(BigInteger userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(BigInteger userId);

}

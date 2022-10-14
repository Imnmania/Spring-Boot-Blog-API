package me.niloybiswas.spblog.services.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.niloybiswas.spblog.entities.User;
import me.niloybiswas.spblog.exceptions.ResourceNotFoundException;
import me.niloybiswas.spblog.payloads.UserDTO;
import me.niloybiswas.spblog.repositories.UserRepo;
import me.niloybiswas.spblog.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		User user= this.dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
		
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, BigInteger userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		UserDTO updatedUserDTO = this.userToDto(updatedUser);
		
		return updatedUserDTO;
		
	}

	@Override
	public UserDTO getUserById(BigInteger userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		return this.userToDto(user);
		
	}

	@Override
	public List<UserDTO> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		
		List<UserDTO> userDTOList = users.stream()
				.map(user -> this.userToDto(user))
				.collect(Collectors.toList());
		
		return userDTOList;
	
	}

	@Override
	public void deleteUser(BigInteger userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		this.userRepo.delete(user);
		
	}
	
	
	// Converting dto to entity
	private User dtoToUser(UserDTO userDTO) {
		/* conversion raw */
		/*
		User user = new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setAbout(userDTO.getAbout());
		return user;
		*/
		
		/* conversion with model mapper */
		User user = this.modelMapper.map(userDTO, User.class);
		return user;
	}
	
	// Converting entity to dto
	private UserDTO userToDto(User user) {
		/*
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setPassword(user.getPassword());
		userDTO.setEmail(user.getEmail());
		userDTO.setAbout(user.getAbout());
		return userDTO;
		*/
		
		/* conversion with model mapper */
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

}

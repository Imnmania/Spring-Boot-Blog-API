package me.niloybiswas.spblog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import me.niloybiswas.spblog.entitiy.Role;
import me.niloybiswas.spblog.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.niloybiswas.spblog.entitiy.User;
import me.niloybiswas.spblog.exception.ResourceNotFoundException;
import me.niloybiswas.spblog.dto.user.UserDTO;
import me.niloybiswas.spblog.repository.UserRepo;
import me.niloybiswas.spblog.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user= this.dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);

		return this.userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Long userId) {
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
	public UserDTO getUserById(Long userId) {
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
	public void deleteUser(Long userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		this.userRepo.delete(user);
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
		// encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		// role assign
		Role role = this.roleRepo.findByName("ROLE_NORMAL");
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(user, UserDTO.class);
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
		return this.modelMapper.map(userDTO, User.class);
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
		return this.modelMapper.map(user, UserDTO.class);
	}

}

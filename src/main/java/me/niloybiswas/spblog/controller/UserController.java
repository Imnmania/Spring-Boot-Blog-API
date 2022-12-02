package me.niloybiswas.spblog.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import me.niloybiswas.spblog.dto.user.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.niloybiswas.spblog.dto.common.ApiResponseDTO;
import me.niloybiswas.spblog.dto.user.UserDTO;
import me.niloybiswas.spblog.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private final UserService userService;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final ModelMapper modelMapper;

	// Create User
	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		UserDTO createdUserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
		
	}
	
	// Update User
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable(name = "userId") Long userId) {
		// if the path variable and function params have different name, use custom name with () after @Pathvariable
		
		UserDTO updatedUserDTO = this.userService.updateUser(userDTO, userId);
		return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
		
	}
	
	// Delete User
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponseDTO> deleteUser(@PathVariable Long userId) {
		
		this.userService.deleteUser(userId);
//		return new ResponseEntity<?>(Map.of("message", "User deleted successfully!"), HttpStatus.OK);
		return new ResponseEntity<>(new ApiResponseDTO("User deleted successfully", true), HttpStatus.OK);
		
	}
	
	
	// Get User
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAll")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		List<UserDTO> users = this.userService.getAllUsers();
		List<UserResponseDTO> usersResponse = users.stream()
				.map(userDTO -> modelMapper.map(userDTO, UserResponseDTO.class))
				.collect(Collectors.toList());
		return new ResponseEntity<>(usersResponse, HttpStatus.OK);
		
	}
	
	// Get User by ID
	@GetMapping("/getById/{userId}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
		UserDTO user = this.userService.getUserById(userId);
		UserResponseDTO response = modelMapper.map(user, UserResponseDTO.class);
			
		return new ResponseEntity<>(response, HttpStatus.OK);
			
	}

	@PostMapping("/addRoleToUser")
	public ResponseEntity<?> addRoleToUser() {
		return  null;
	}
	
	

}

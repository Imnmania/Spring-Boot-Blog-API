package me.niloybiswas.spblog.controllers;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.niloybiswas.spblog.payloads.ApiResponseDTO;
import me.niloybiswas.spblog.payloads.UserDTO;
import me.niloybiswas.spblog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// Create User
	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		
		UserDTO createdUserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<UserDTO>(createdUserDTO, HttpStatus.CREATED);
		
	}
	
	// Update User
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable(name = "userId") BigInteger userId) {
		// if the path variable and function params have different name, use custom name with () after @Pathvariable
		
		UserDTO updatedUserDTO = this.userService.updateUser(userDTO, userId);
		return new ResponseEntity<UserDTO>(updatedUserDTO, HttpStatus.OK);
		
	}
	
	// Delete User
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponseDTO> deleteUser(@PathVariable BigInteger userId) {
		
		this.userService.deleteUser(userId);
//		return new ResponseEntity<?>(Map.of("message", "User deleted successfully!"), HttpStatus.OK);
		return new ResponseEntity<ApiResponseDTO>(new ApiResponseDTO("User deleted successfully", true), HttpStatus.OK);
		
	}
	
	
	// Get User
	@GetMapping("/getAll")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		
		return new ResponseEntity<List<UserDTO>>(this.userService.getAllUsers(), HttpStatus.OK);
		
	}
	
	// Get User by ID
	@GetMapping("/getById/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable BigInteger userId) {
			
		return new ResponseEntity<UserDTO>(this.userService.getUserById(userId), HttpStatus.OK);
			
	}
	
	

}

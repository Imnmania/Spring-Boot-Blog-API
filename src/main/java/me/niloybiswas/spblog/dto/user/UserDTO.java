package me.niloybiswas.spblog.dto.user;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.niloybiswas.spblog.entitiy.Role;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private Long id;
	
	@NotEmpty
	@Size(min = 3, message = "username must be at least 3 characters long")
	private String name;
	
	@NotEmpty
	@Email(message = "email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "password must be 3-10 characters long")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "special Characters not allowed for password")
	private String password;
	
	@NotEmpty
	private String about;

	private Set<Role> roles;
}

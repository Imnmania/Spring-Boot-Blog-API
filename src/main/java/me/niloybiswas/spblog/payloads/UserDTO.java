package me.niloybiswas.spblog.payloads;

import java.math.BigInteger;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private BigInteger id;
	
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

}

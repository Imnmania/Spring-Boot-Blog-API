package me.niloybiswas.spblog.payloads;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private BigInteger id;
	private String name;
	private String email;
	private String password;
	private String about;

}

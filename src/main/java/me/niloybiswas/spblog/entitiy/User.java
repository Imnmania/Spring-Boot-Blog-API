package me.niloybiswas.spblog.entitiy;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter 
@Setter
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;
	
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;
	
	private String email;
	
	private String password;
	
	private String about;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Post> posts;
	
}

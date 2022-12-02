package me.niloybiswas.spblog.entitiy;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;
	
	private String email;
	
	private String password;
	
	private String about;

	@OneToMany(mappedBy = "user",  orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Post> posts;

	@OneToMany(mappedBy = "user",  orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
	)
	private Set<Role> roles = new HashSet<>();


	// overriding UserDetails methods from spring security
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

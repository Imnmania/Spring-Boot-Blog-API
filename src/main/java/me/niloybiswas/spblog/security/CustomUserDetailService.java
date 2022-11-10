package me.niloybiswas.spblog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.niloybiswas.spblog.entitiy.User;
import me.niloybiswas.spblog.exception.ResourceNotFoundException;
import me.niloybiswas.spblog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // load user from db by username (we are doing with email)
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(() ->new ResourceNotFoundException("User", "email", username));

        return user;
    }
}

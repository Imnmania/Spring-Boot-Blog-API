package me.niloybiswas.spblog.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.niloybiswas.spblog.dto.security.JwtAuthRequestDTO;
import me.niloybiswas.spblog.dto.security.JwtAuthResponseDTO;
import me.niloybiswas.spblog.dto.user.UserDTO;
import me.niloybiswas.spblog.security.JwtTokenHelper;
import me.niloybiswas.spblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> createToken(@RequestBody JwtAuthRequestDTO request) {

        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponseDTO response = new JwtAuthResponseDTO();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.registerUser(userDTO);
        if (user == null) {
            log.error("User already exists!!!");
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    ///* This does not work with JWT, users have to delete the JWT's from frontend/apps
    /*@GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            Map<String, Object> res = new HashMap<>();
            res.put("message", "Successfully logged out!");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("message", "Log out failed!");
        return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
    }*/

    private void authenticate(String username, String password) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }
}

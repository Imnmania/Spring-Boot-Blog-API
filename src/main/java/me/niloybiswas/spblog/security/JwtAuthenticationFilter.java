package me.niloybiswas.spblog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.niloybiswas.spblog.exception.InvalidTokenException;
import me.niloybiswas.spblog.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired()
    @Qualifier("customUserDetailService")
    private final UserDetailsService userDetailsService;

    @Autowired
    private final JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        /*
         * 1. Get bearer token & username from token
         */
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String username = null;
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            try {
                token = authorizationHeader.substring("Bearer ".length());
                username = this.jwtTokenHelper.getUserNameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT token!!!");
//                throw e;
            } catch (ExpiredJwtException e) {
                logger.error("JWT token has expired!!!");
//                throw e;
            } catch (MalformedJwtException e) {
                logger.error("Malformed JWT!!!");
//                throw e;
            }
        } else {
            logger.error("JWT token does not begin with 'Bearer'");
        }

        /*
         * 2. Once we get the token & username, validate token & authenticate
         */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // everything is fine
                // do the authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                logger.error("Invalid JWT!!!");
            }
        } else {
            logger.error("Username or Security Context not found!!!");
        }

        filterChain.doFilter(request, response);
    }

    // do not use this elsewhere
    /*private void customAuthResponseMessage(String message, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }*/
}

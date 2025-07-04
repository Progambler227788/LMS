package com.codingwork.lms.security;


import com.codingwork.lms.repository.UserRepository;
import com.codingwork.lms.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


// Intercepts every request and extracts the JWT token from the Authorization header.
/*
Check if the request has an Authorization header that starts with Bearer
Extract and validate the token using JwtUtil.
Set authentication in SecurityContextHolder if the token is valid.
* */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Skipping the string Bearer
        String token = authHeader.substring(7);
        String userName = jwtUtil.extractUserName(token);


        // If the userName is not null and there is no authentication in the context
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details by username i.e. userName
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (userDetails != null && jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}


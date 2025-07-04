package com.codingwork.lms.service.impl;

// I added these comments so that I don't forget my codebase XD :)
// UserDetailsServiceImpl is only responsible for fetching authentication-related user details.
// UserDetailsService is a Spring Security interface that provides a method loadUserByUsername(String username)


import com.codingwork.lms.entity.User;
import com.codingwork.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get the user from the database
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // Create authority from the single role
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
            List<GrantedAuthority> authorities = Collections.singletonList(authority);

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }


}


package com.codingwork.lms.service.impl;

// I added these comments so that I don't forget my codebase XD :)
// UserDetailsServiceImpl is only responsible for fetching authentication-related user details.
// UserDetailsService is a Spring Security interface that provides a method loadUserByUsername(String username)


import com.codingwork.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}


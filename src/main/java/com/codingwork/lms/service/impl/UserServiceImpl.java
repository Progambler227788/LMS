package com.codingwork.lms.service.impl;

import com.codingwork.lms.dto.request.user.*;
import com.codingwork.lms.dto.response.user.*;
import com.codingwork.lms.entity.User;
import com.codingwork.lms.exception.ResourceMissingException;
import com.codingwork.lms.mapper.UserMapper;
import com.codingwork.lms.repository.UserRepository;
import com.codingwork.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceMissingException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        User user = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceMissingException("User not found with id: " + id));

        if (request.username() != null) user.setUsername(request.username());
        if (request.email() != null) user.setEmail(request.email());
        if (request.password() != null) user.setPassword(passwordEncoder.encode(request.password()));
        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(new ObjectId(id))) {
            throw new ResourceMissingException("User not found with id: " + id);
        }
        userRepository.deleteById(new ObjectId(id));
    }

    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceMissingException("User not found with username: " + username));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceMissingException("User not found with email: " + email));
        return userMapper.toResponse(user);
    }


}
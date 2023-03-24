package com.netblog.netblog.services.impl;

import com.netblog.netblog.dtos.CreateUserDto;
import com.netblog.netblog.dtos.UpdateUserDto;
import com.netblog.netblog.dtos.UserResponse;
import com.netblog.netblog.exceptions.PasswordFieldEmptyException;
import com.netblog.netblog.exceptions.PasswordsDoNotMatchException;
import com.netblog.netblog.exceptions.UsernameAlreadyExistsException;
import com.netblog.netblog.exceptions.UsernameNotFoundException;
import com.netblog.netblog.models.User;
import com.netblog.netblog.repositories.UserRepository;
import com.netblog.netblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    GET
    @Override
    public UserResponse userById(String username) {
        User user =  this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return userToUserResponse(user);
    }

    @Override
    public List<UserResponse> allUsers() {
        List<User> users = this.userRepository.findAll();

        return users.stream().map(this::userToUserResponse).toList();
    }


    //    POST
    @Override
    public UserResponse createUser(CreateUserDto request) {
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new PasswordsDoNotMatchException("Passwords do not match");
        }

        Optional<User> userOptional = this.userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User user = new User();

        String passwordHash = this.passwordEncoder.encode((request.getPassword()));

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordHash);
        user.setCreated(new Date(System.currentTimeMillis()));

        this.userRepository.save(user);

        return userToUserResponse(user);
    }

//    PUT
    @Override
    public UserResponse updateUser(String username, UpdateUserDto request) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getNewPassword() != null) {
            String currentPasswordHash = this.passwordEncoder.encode(request.getCurrentPassword());

            if (request.getCurrentPassword() == null || request.getNewPasswordConfirm() == null) {
                throw new PasswordFieldEmptyException("One of your password fields is missing");
            } else if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
                throw new PasswordsDoNotMatchException("New passwords do not match");
            } else if (!user.getPasswordHash().equals(currentPasswordHash)) {
                throw new PasswordsDoNotMatchException("Current password is incorrect");
            }

            String newPasswordHash = this.passwordEncoder.encode(request.getNewPassword());

            user.setPasswordHash(newPasswordHash);
        }

        this.userRepository.save(user);

        return userToUserResponse(user);
    }

//    DELETE
    @Override
    public void deleteUser(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        this.userRepository.delete(user);
    }

    //    Methods
    private UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreated(user.getCreated());

        return userResponse;
    }
}

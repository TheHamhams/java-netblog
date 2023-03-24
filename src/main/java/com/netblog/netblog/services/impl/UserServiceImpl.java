package com.netblog.netblog.services.impl;

import com.netblog.netblog.dtos.CreateUserDto;
import com.netblog.netblog.dtos.UserResponse;
import com.netblog.netblog.exceptions.PasswordsDoNotMatchException;
import com.netblog.netblog.exceptions.UsernameAlreadyExistsException;
import com.netblog.netblog.exceptions.UsernameNotFoundException;
import com.netblog.netblog.models.User;
import com.netblog.netblog.repositories.UserRepository;
import com.netblog.netblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    GET
    @Override
    public UserResponse userById(String username) {
        User user =  this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return userToUserResponse(user);
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

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCreated(new Date(System.currentTimeMillis()));

        this.userRepository.save(user);

        return userToUserResponse(user);
    }



//    Methods
    private UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }
}

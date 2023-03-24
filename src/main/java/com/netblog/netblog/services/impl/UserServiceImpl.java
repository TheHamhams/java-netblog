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


/**
 * Implementation of UserService interface. Provides methods for managing users, such as
 * creating, updating, deleting, and retrieving user information.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor for UserServiceImpl. Initializes the userRepository and passwordEncoder fields.
     * @param userRepository an instance of UserRepository used to access and manipulate user data
     * @param passwordEncoder an instance of BCryptPasswordEncoder used to encode user passwords
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    GET

    /**
     * Retrieves user information for the given username.
     * @param username the username of the user to retrieve
     * @return a UserResponse object containing the user information
     * @throws UsernameNotFoundException if the username is not found in the database
     */
    @Override
    public UserResponse userById(String username) {
        User user =  this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return userToUserResponse(user);
    }

    /**
     * Retrieves information for all users in the database.
     * @return a List of UserResponse objects containing user information
     */
    @Override
    public List<UserResponse> allUsers() {
        List<User> users = this.userRepository.findAll();

        return users.stream().map(this::userToUserResponse).toList();
    }


    //    POST

    /**
     * Creates a new user with the given information.
     * @param request a CreateUserDto object containing the user information to create
     * @return a UserResponse object containing the created user information
     * @throws PasswordsDoNotMatchException if the password and passwordConfirm fields do not match
     * @throws UsernameAlreadyExistsException if the given username already exists in the database
     */
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

    /**
     * Updates the user information for the given username.
     * @param username the username of the user to update
     * @param request an UpdateUserDto object containing the user information to update
     * @return a UserResponse object containing the updated user information
     * @throws PasswordFieldEmptyException if either the currentPassword or newPasswordConfirm fields are missing
     * @throws PasswordsDoNotMatchException if the new passwords do not match or if the current password is incorrect
     * @throws UsernameNotFoundException if the username is not found in the database
     */
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

    /**
     * Deletes the user with the given username.
     * @param username the username of the user to delete
     * @throws UsernameNotFoundException if the username is not found in the database
     */
    @Override
    public void deleteUser(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        this.userRepository.delete(user);
    }

    //    Methods

    /**
     * Converts a User object to a UserResponse object.
     * @param user the User object to convert
     * @return a UserResponse object containing the user information
     */
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

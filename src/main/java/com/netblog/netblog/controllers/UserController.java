package com.netblog.netblog.controllers;


import com.netblog.netblog.dtos.CreateUserDto;
import com.netblog.netblog.dtos.UpdateUserDto;
import com.netblog.netblog.dtos.UserResponse;
import com.netblog.netblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for handling user-related HTTP requests.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService the service responsible for handling user data.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    GET

    /**
     * Retrieves a user's information by their username.
     *
     * @param username the username of the user to retrieve.
     * @return a ResponseEntity containing the user's information and a status code.
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(this.userService.userById(username));
    }

    /**
     * Retrieves all users' information.
     *
     * @return a ResponseEntity containing a list of UserResponses and a status code.
     */
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(this.userService.allUsers());
    }

//    POST

    /**
     * Creates a new user.
     *
     * @param request the CreateUserDto object containing the new user's information.
     * @return a ResponseEntity containing the newly created user's information and a status code.
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> postNewUser(@RequestBody CreateUserDto request) {
        return ResponseEntity.ok(this.userService.createUser(request));
    }

//    PUT

    /**
     * Updates a user's information.
     *
     * @param username the username of the user to update.
     * @param request the UpdateUserDto object containing the new user information.
     * @return a ResponseEntity containing the updated user's information and a status code.
     */
    @PutMapping("/update/{username}")
    public ResponseEntity<UserResponse> putUser(@PathVariable("username") String username, @RequestBody UpdateUserDto request) {
        return ResponseEntity.ok(this.userService.updateUser(username, request));
    }

//    DELETE

    /**
     * Deletes a user.
     *
     * @param username the username of the user to delete.
     * @return a ResponseEntity containing a message indicating the user was deleted and a status code.
     */
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        this.userService.deleteUser(username);
        return ResponseEntity.ok("Deleted");
    }

}

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

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    GET
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(this.userService.userById(username));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(this.userService.allUsers());
    }

//    POST
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> postNewUser(@RequestBody CreateUserDto request) {
        return ResponseEntity.ok(this.userService.createUser(request));
    }

//    PUT
    @PutMapping("/update/{username}")
    public ResponseEntity<UserResponse> putUser(@PathVariable("username") String username, @RequestBody UpdateUserDto request) {
        return ResponseEntity.ok(this.userService.updateUser(username, request));
    }

//    DELETE

}

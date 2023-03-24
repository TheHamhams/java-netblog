package com.netblog.netblog.controllers;


import com.netblog.netblog.dtos.CreateUserDto;
import com.netblog.netblog.dtos.UserResponse;
import com.netblog.netblog.models.User;
import com.netblog.netblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    GET
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(this.userService.userById(username));
    }

//    POST
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createNewUser(@RequestBody CreateUserDto request) {
        return ResponseEntity.ok(this.userService.createUser(request));
    }

}

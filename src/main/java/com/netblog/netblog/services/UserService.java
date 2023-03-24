package com.netblog.netblog.services;

import com.netblog.netblog.models.User;
import com.netblog.netblog.dtos.CreateUserDto;
import com.netblog.netblog.dtos.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserDto request);

    UserResponse userById(String username);

    List<UserResponse> allUsers();
}

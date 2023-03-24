package com.netblog.netblog.services;

import com.netblog.netblog.dtos.UpdateUserDto;
import com.netblog.netblog.dtos.CreateUserDto;
import com.netblog.netblog.dtos.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserDto request);

    UserResponse userById(String username);

    List<UserResponse> allUsers();

    UserResponse updateUser(String username, UpdateUserDto request);

    void deleteUser(String username);
}

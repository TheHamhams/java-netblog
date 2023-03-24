package com.netblog.netblog.services;

import com.netblog.netblog.dtos.CreateUserDto;
import com.netblog.netblog.dtos.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserDto request);

    UserResponse userById(String username);
}

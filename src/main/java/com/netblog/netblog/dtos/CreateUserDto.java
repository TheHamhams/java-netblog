package com.netblog.netblog.dtos;

import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;
}

package com.netblog.netblog.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date created;
}

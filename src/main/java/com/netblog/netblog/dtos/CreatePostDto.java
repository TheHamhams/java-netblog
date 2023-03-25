package com.netblog.netblog.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CreatePostDto {

    private String title;
    private String content;
    private String userUsername;
}

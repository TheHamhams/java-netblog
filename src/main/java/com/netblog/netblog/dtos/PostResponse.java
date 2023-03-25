package com.netblog.netblog.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PostResponse {
    private Integer id;
    private String title;
    private String content;
    private String userUsername;
    private Date created;

}

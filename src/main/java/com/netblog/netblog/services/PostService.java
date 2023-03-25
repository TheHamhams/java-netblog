package com.netblog.netblog.services;

import com.netblog.netblog.dtos.CreatePostDto;
import com.netblog.netblog.dtos.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(CreatePostDto request);

    List<PostResponse> allPosts();


    List<PostResponse> allPostsByUserUsername(String username);

    PostResponse postById(Integer id);
}

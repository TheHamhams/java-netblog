package com.netblog.netblog.controllers;


import com.netblog.netblog.dtos.CreatePostDto;
import com.netblog.netblog.dtos.PostResponse;
import com.netblog.netblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

//    GET

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPosts () {
        return ResponseEntity.ok(this.postService.allPosts());
    }


//    POST

    @PostMapping("/create")
    public ResponseEntity<PostResponse> postPost(@RequestBody CreatePostDto request) {
        return ResponseEntity.ok(this.postService.createPost(request));
    }

//    PUT



//    DELETE


}

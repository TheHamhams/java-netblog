package com.netblog.netblog.services.impl;

import com.netblog.netblog.dtos.CreatePostDto;
import com.netblog.netblog.dtos.PostResponse;
import com.netblog.netblog.exceptions.UsernameNotFoundException;
import com.netblog.netblog.models.Post;
import com.netblog.netblog.models.User;
import com.netblog.netblog.repositories.PostRepository;
import com.netblog.netblog.repositories.UserRepository;
import com.netblog.netblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

//    GET

    @Override
    public List<PostResponse> allPosts() {
        List<Post> posts = this.postRepository.findAll();

        return posts.stream().map(this::postToPostResponse).toList();
    }

//    POST

    @Override
    public PostResponse createPost(CreatePostDto request) {
        User user = this.userRepository.findByUsername(request.getUserUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Post post = new Post();

        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreated(new Date(System.currentTimeMillis()));

        this.postRepository.save(post);

        return postToPostResponse(post);
    }



//    PUT



//    DELETE


//    Methods

    public PostResponse postToPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();

        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setCreated(post.getCreated());
        postResponse.setUserUsername(post.getUser().getUsername());

        return postResponse;
    }

}

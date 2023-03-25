package com.netblog.netblog.repositories;

import com.netblog.netblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserUsername(String username);
}

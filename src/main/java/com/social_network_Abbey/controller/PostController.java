package com.social_network_Abbey.controller;

import com.social_network_Abbey.dto.PostDTO;
import com.social_network_Abbey.dto.UserDTO;
import com.social_network_Abbey.entity.Post;
import com.social_network_Abbey.response.CustomResponse;
import com.social_network_Abbey.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Object>> createPost(@RequestBody Post postRequest) {
        CustomResponse<Object> response = postService.createPost(postRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse<Object>> deletePost(@PathVariable Long id) {
        CustomResponse<Object> response = postService.deletePost(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/all")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<CustomResponse<Object>> getPostById(@PathVariable Long id) {
        CustomResponse<Object> response = postService.getPostById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomResponse<Object>> getAllPostsByUser(@PathVariable Long userId) {
        CustomResponse<Object> response = postService.getAllPostsByUser(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/user/{userId}/following")
    public ResponseEntity<CustomResponse<Object>> getPostsByUserFollowing(@PathVariable Long userId) {
        CustomResponse<Object> response = postService.getPostsByUserFollowing(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}

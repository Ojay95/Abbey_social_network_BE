package com.social_network_Abbey.controller;


import com.social_network_Abbey.request.LikeRequest;
import com.social_network_Abbey.response.CustomResponse;
import com.social_network_Abbey.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Object>> create(@RequestBody LikeRequest likeRequest) {
        CustomResponse<Object> response = likeService.create(likeRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<CustomResponse<Object>> getAllLikesByPost(@PathVariable Long postId) {
        CustomResponse<Object> response = likeService.getAllByPost(postId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomResponse<Object>> getAllLikesByUser(@PathVariable Long userId) {
        CustomResponse<Object> response = likeService.getAllByUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/likeStatus")
    public ResponseEntity<CustomResponse<Object>> checkLikeStatus(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "postId") Long postId) {
        CustomResponse<Object> response = likeService.isLiked(userId, postId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<CustomResponse<Object>> deleteLike(@RequestBody LikeRequest likeRequest) {
        CustomResponse<Object> response = likeService.delete(likeRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}


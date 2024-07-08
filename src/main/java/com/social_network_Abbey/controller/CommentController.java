package com.social_network_Abbey.controller;

import com.social_network_Abbey.entity.Comment;
import com.social_network_Abbey.service.ApplicationUserService;
import com.social_network_Abbey.service.CommentService;
import com.social_network_Abbey.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")

public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final ApplicationUserService userService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, ApplicationUserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> getCommentsByUserId(@PathVariable Long userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment newComment) {
        Comment createdComment = commentService.createComment(newComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        Comment comment = commentService.updateComment(commentId, updatedComment);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

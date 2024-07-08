package com.social_network_Abbey.service;
import com.social_network_Abbey.entity.Comment;
import com.social_network_Abbey.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ApplicationUserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostService postService, ApplicationUserService userService, ApplicationUserService userService1) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public Comment getCommentById(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        return commentOptional.orElse(null);
    }

    public Comment createComment(Comment newComment) {
        return commentRepository.save(newComment);
    }

    public Comment updateComment(Long commentId, Comment updatedComment) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
        if (existingCommentOptional.isPresent()) {
            Comment existingComment = existingCommentOptional.get();
            return commentRepository.save(existingComment);
        } else {
            return null;
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

package com.social_network_Abbey.service;

import com.social_network_Abbey.entity.Like;
import com.social_network_Abbey.repository.LikeRepository;
import com.social_network_Abbey.request.LikeRequest;
import com.social_network_Abbey.response.CustomResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository){
        this.likeRepository = likeRepository;
    }

    // Creates a new like
    public CustomResponse<Object> create(LikeRequest likeRequest) {
        try {
            Like like = new Like();
            BeanUtils.copyProperties(likeRequest, like);
            likeRepository.save(like);
            return new CustomResponse<>(true, false, "Like created successfully", HttpStatus.CREATED.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "Like creation failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // Retrieves all likes for a specific post
    public CustomResponse<Object> getAllByPost(Long postId) {
        try {
            List<Like> likes = likeRepository.findAllByPostId(postId);
            return new CustomResponse<>(true, false, String.valueOf(likes), HttpStatus.OK.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "Error occurred while fetching likes", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // Retrieves all likes made by a specific user
    public CustomResponse<Object> getAllByUser(Long userId) {
        try {
            List<Like> likes = likeRepository.findAllByUserId(userId);
            return new CustomResponse<>(true, false, String.valueOf(likes), HttpStatus.OK.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "Error occurred while fetching likes", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // Checks if a post is liked by a specific user
    public CustomResponse<Object> isLiked(Long userId, Long postId) {
        Optional<Like> like = likeRepository.findByUserIdAndPostId(userId, postId);
        if (like.isPresent()) {
            return new CustomResponse<>(true, false, "Post is liked", HttpStatus.OK.value());
        }
        return new CustomResponse<>(true, false, "Post is not liked", HttpStatus.OK.value());
    }

    // Deletes a like
    public CustomResponse<Object> delete(LikeRequest likeRequest) {
        Optional<Like> like = likeRepository.findByUserIdAndPostId(likeRequest.getUserId(), likeRequest.getPostId());
        if (like.isPresent()) {
            likeRepository.delete(like.get());
            return new CustomResponse<>(true, false, "Post unliked", HttpStatus.OK.value());
        }
        return new CustomResponse<>(false, true, "Like not found", HttpStatus.NOT_FOUND.value());
    }
}

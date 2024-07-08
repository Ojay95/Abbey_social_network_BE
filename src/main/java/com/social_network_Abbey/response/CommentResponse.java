package com.social_network_Abbey.response;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private String username;
    private String content;
    private String createdAt;
}

package com.social_network_Abbey.request;

import lombok.Data;

@Data
public class CommentRequest {
    private Long postId;
    private Long author_Id;
    private String content;
}

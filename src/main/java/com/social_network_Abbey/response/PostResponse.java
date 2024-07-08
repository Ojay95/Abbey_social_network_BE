package com.social_network_Abbey.response;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private Long userId;
    private String content;
    private String postImage;
}

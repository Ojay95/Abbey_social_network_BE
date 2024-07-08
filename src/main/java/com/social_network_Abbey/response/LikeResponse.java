package com.social_network_Abbey.response;

import lombok.Data;

@Data
public class LikeResponse {
    private Long id;
    private Long userId;
    private Long postId;
}

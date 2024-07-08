package com.social_network_Abbey.request;

import lombok.Data;

@Data
public class LikeRequest {
    private Long userId;
    private Long postId;
}

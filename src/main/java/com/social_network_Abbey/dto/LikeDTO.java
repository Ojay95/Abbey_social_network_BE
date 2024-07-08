package com.social_network_Abbey.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {
    private Long id;
    private Long userId;
    private Long postId;
}

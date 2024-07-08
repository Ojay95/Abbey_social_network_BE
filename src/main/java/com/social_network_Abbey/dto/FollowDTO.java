package com.social_network_Abbey.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDTO {
    private Long id;
    private Long followerId;
    private Long followingId;
}

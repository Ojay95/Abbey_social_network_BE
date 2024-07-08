package com.social_network_Abbey.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Long author_Id;
    private String content;
    private String postImage;
    private LocalDateTime createdAt;
}

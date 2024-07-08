package com.social_network_Abbey.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private Long authorId;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
}

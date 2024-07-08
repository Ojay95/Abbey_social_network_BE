package com.social_network_Abbey.request;

import lombok.Data;

@Data
public class PostRequest {
        private Long authorId;
        private String content;
        private String postImage;
      }

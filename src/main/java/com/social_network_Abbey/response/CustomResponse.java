package com.social_network_Abbey.response;

import lombok.Data;

@Data
public class CustomResponse<T> {
        private boolean success;
        private boolean error;
        private String message;
        private int statusCode;
        private T data;

        public CustomResponse(boolean success, boolean error, String message, int statusCode) {
            this.success = success;
            this.error = error;
            this.message = message;
            this.statusCode = statusCode;
        }

        public CustomResponse(boolean success, boolean error, String message, int statusCode, T data) {
            this.success = success;
            this.error = error;
            this.message = message;
            this.statusCode = statusCode;
            this.data = data;
        }

    public CustomResponse(boolean success, boolean error, PostResponse postResponse, Long value) {
    }


}

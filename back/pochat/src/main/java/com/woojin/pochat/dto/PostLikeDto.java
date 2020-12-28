package com.woojin.pochat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

public class PostLikeDto {
    @NoArgsConstructor
    @Getter
    public static class PostLikeCreateRequestDto{
        private Long id;

        public PostLikeCreateRequestDto(String id) {
            Assert.notNull(id, "id must be not null");

            this.id = Long.parseLong(id);
        }
    }
}

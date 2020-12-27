package com.woojin.pochat.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

public class PostDto {

    @NoArgsConstructor
    @Data
    public static class PostCreateRequestDto {
        private String title;
        private String shortDescription;
        private String body;
        private Boolean isPrivate;   // 그룹원 or 개인만

        public PostCreateRequestDto(String title, String shortDescription, String body, Boolean isPrivate) {
            Assert.notNull(title, "title must be not null");
            Assert.notNull(shortDescription, "shortDescription must be not null");
            Assert.notNull(body, "body must be not null");
            Assert.notNull(isPrivate, "isPrivate must be not null");

            this.title = title;
            this.shortDescription = shortDescription;
            this.body = body;
            this.isPrivate = isPrivate;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class PostCreateResponseDto{
        private String title;
        private String body;

        public PostCreateResponseDto(String title, String body, String url) {
            Assert.notNull(title, "title must be not null");
            Assert.notNull(body, "body must be not null");

            this.title = title;
            this.body = body;
        }
    }

    @NoArgsConstructor
    @Data
    public static class PostUpdateFavoriteRequestDto{
        private Long id;

        public PostUpdateFavoriteRequestDto(String id) {
            Assert.notNull(id, "id must be not null");

            this.id = Long.parseLong(id);
        }
    }

    @NoArgsConstructor
    @Getter
    public static class PostUpdateRequestDto{
        private String title;
        private String shortDescription;
        private String body;
        private Boolean isPrivate;


        public PostUpdateRequestDto(String title, String body, String shortDescription, Boolean isPrivate) {
            Assert.notNull(title, "title must be not null");
            Assert.notNull(body, "body must be not null");
            Assert.notNull(shortDescription, "shortDescription must be not null");
            Assert.notNull(isPrivate, "isPrivate must be not null");

            this.title = title;
            this.body = body;
            this.shortDescription = shortDescription;
            this.isPrivate = isPrivate;
        }
    }

}

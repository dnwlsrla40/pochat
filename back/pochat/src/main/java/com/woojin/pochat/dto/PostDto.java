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
        private Long chatId;

        public PostCreateRequestDto(String title, String shortDescription, String body, Boolean isPrivate, String chatId) {
            Assert.notNull(title, "title must be not null");
            Assert.notNull(shortDescription, "shortDescription must be not null");
            Assert.notNull(body, "body must be not null");
            Assert.notNull(isPrivate, "isPrivate must be not null");
            Assert.notNull(chatId, "chatId must be not null");

            this.title = title;
            this.shortDescription = shortDescription;
            this.body = body;
            this.isPrivate = isPrivate;
            this.chatId = Long.parseLong(chatId);
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
    @Getter
    public static class PostUpdateRequestDto{
        private Long id;
        private String title;
        private String shortDescription;
        private String body;
        private Boolean isPrivate;


        public PostUpdateRequestDto(String id, String title, String body, String shortDescription, Boolean isPrivate) {
            Assert.notNull(id, "Id must be not null");
            Assert.notNull(title, "title must be not null");
            Assert.notNull(body, "body must be not null");
            Assert.notNull(shortDescription, "shortDescription must be not null");
            Assert.notNull(isPrivate, "isPrivate must be not null");

            this.id = Long.parseLong(id);
            this.title = title;
            this.body = body;
            this.shortDescription = shortDescription;
            this.isPrivate = isPrivate;
        }
    }

}

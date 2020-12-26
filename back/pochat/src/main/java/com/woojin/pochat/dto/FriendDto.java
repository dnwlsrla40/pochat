package com.woojin.pochat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

public class FriendDto {
    @NoArgsConstructor
    @Data
    public static class FriendCreateRequestDto {
//        private String senderName;
        private String recipientName;

        public FriendCreateRequestDto(String recipientName) {
//            Assert.notNull(senderName, "senderName must be not null");
            Assert.notNull(recipientName, "recipientName must be not null");

//            this.senderName = senderName;
            this.recipientName = recipientName;
        }
    }

    @NoArgsConstructor
    @Data
    public static class FriendAcceptRequestDto {
        private String senderName;
        private String recipientName;

        public FriendAcceptRequestDto(String senderName, String recipientName) {
            Assert.notNull(senderName, "senderName must be not null");
            Assert.notNull(recipientName, "recipientName must be not null");

            this.senderName = senderName;
            this.recipientName = recipientName;
        }
    }

    @NoArgsConstructor
    @Data
    public static class FriendCancelRequestDto {
        private String cancelUser;
        private String canceledUser;

        public FriendCancelRequestDto(String canceledUser, String cancelUser) {
            Assert.notNull(cancelUser, "cancelUser must be not null");
            Assert.notNull(canceledUser, "canceledUser must be not null");

            this.cancelUser = cancelUser;
            this.canceledUser = canceledUser;
        }
    }
}

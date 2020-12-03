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
}

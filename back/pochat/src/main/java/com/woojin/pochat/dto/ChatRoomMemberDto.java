package com.woojin.pochat.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ChatRoomMemberDto {
    @NoArgsConstructor
    @Data
    public static class ChatRoomMemberCreateRequestDto {
        private String id;
        private List<String> newMember;

        @Builder
        public ChatRoomMemberCreateRequestDto(String id, List<String> newMember){
            this.id = id;
            this.newMember = newMember;
        }
    }
}

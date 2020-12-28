package com.woojin.pochat.dto;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class ChatRoomDto {
    @NoArgsConstructor
    @Data
    public static class ChatRoomCreateRequestDto {
        private String name;
        private List<String> roomMember;

        @Builder
        public ChatRoomCreateRequestDto(String name, List<String> roomMember){
            this.name = name;
            this.roomMember = roomMember;
        }
    }
}

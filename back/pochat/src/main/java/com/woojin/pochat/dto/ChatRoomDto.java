package com.woojin.pochat.dto;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ChatRoomDto {
    @NoArgsConstructor
    @Data
    public static class ChatRoomCreateRequestDto {
        private String name;

        public ChatRoom toEntity(){
            return ChatRoom.builder()
                    .name(this.name)
                    .build();
        }
    }
}

package com.woojin.pochat.mapper;

import lombok.Data;

@Data
public class FriendParameter {
    private Long userId;
    private Long chatRoomId;

    public FriendParameter(Long userId, Long chatRoomId){
        this.userId = userId;
        this.chatRoomId = chatRoomId;
    }
}

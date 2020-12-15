package com.woojin.pochat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WebSocketChatMessage {
    private String type;
    private String message;
    private String username;
}

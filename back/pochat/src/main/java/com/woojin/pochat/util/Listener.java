package com.woojin.pochat.util;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woojin.pochat.domain.WebSocketChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;

@Component
public class Listener {
    private SimpMessagingTemplate template;

    @Autowired
    public Listener(SimpMessagingTemplate template){
        this.template = template;
    }

    public void processMessage(byte[] bytes) throws UnsupportedEncodingException {
        //bytes.
        String str = new String(bytes, "UTF-8");
//        for(byte x : bytes){
//            if((char)x != '\\') {
//                str += (char) x;
//                System.out.println("x : " + x);
//            }
//            System.out.println("str : " + str);
//        }
        System.out.println("============================================Listener"+str);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try{
            WebSocketChatMessage webSocketChatMessage = objectMapper.readValue(str, WebSocketChatMessage.class);
            System.out.println(webSocketChatMessage.getMessage());
            System.out.println("================================/topic/" + webSocketChatMessage.getChannel());
            this.template.convertAndSend("/topic/"+webSocketChatMessage.getChannel(), webSocketChatMessage);
        }catch(Exception e){
            System.out.println(e);
        }
    }


}

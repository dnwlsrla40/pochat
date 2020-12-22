package com.woojin.pochat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

// Stomp 사용
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketChatConfig implements WebSocketMessageBrokerConfigurer {
//    private final WebSocketHandler webSocketHandler;

    // CORS 허용(도메인이 다른 서버에서도 접속 가능) 및 end point를 /ws/chat으로 설정
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
//        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
//    }
    /*
        reference 용어
        /topic : 암시적으로 1:N 전파
        /queue : 암시적으로 1:1 전파
     */

    /*
         pub/sub 메시징 구현을 위한 설정
         발행 요청 prefix : /pub -> /app
         구독 요청 prefix : /sub -> /topic
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
//        config.enableSimpleBroker("/sub");  // SimpleBroker 등록 해당 경로를 subscribe하는 client에게 메시지 전달
        config.setApplicationDestinationPrefixes("/app");   // client Send요청
        config.enableStompBrokerRelay("/topic").setRelayHost("localhost").setRelayPort(61613).setClientLogin("guest").setClientPasscode("guest"); // SimpleBroker 기능과 외부 Message Broker에 메시지 전달
    }

    /*
        stomp websocker 의 endpoint 설정
        endpoing : /ws-stomp -> ws://localhost:8080/ws-stomp
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/websocketApp").setAllowedOrigins("*")
                .withSockJS();
    }
}


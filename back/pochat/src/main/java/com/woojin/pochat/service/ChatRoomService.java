package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroommember.ChatRoomMember;
import com.woojin.pochat.domain.chatroommember.ChatRoomMemberRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.ChatRoomDto;
import com.woojin.pochat.util.Listener;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final TopicExchange exchange;

    @Autowired
    private final SimpleMessageListenerContainer container;

    @Autowired
    private final Listener listener;

    @Transactional
    public List<ChatRoom> getChatRoomList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return chatRoomMemberRepository.getChatRoomByUser(loginUser);
    }

    @Transactional
    public ChatRoom create(ChatRoomDto.ChatRoomCreateRequestDto requestDto){
        ChatRoom chatRoom = ChatRoom.builder()
                .name(requestDto.getName())
                .build();

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .user(loginUser)
                .chatRoom(chatRoom)
                .build();

        ChatRoom createdRoom = chatRoomMemberRepository.save(chatRoomMember).getChatRoom();

        for (int i = 0; i < requestDto.getRoomMember().size(); i++) {
            System.out.println(requestDto.getRoomMember().get(i));
            User Member = userRepository.findByUsername(requestDto.getRoomMember().get(i)).orElseThrow(NoSuchElementException::new);
            chatRoomMember = ChatRoomMember.builder()
                    .user(Member)
                    .chatRoom(chatRoom)
                    .build();;
            chatRoomMemberRepository.save(chatRoomMember);
        }

        createQueue(requestDto.getName(), chatRoom.getId());
        return createdRoom;
    }

    // RabbitMq 동적 Queue 생성
    public void createQueue(String chatRoomName, Long chatRoomId){
        CachingConnectionFactory cf = new CachingConnectionFactory("127.0.0.1", 5672);
        cf.setUsername("guest");
        cf.setPassword("guest");

        RabbitAdmin admin = new RabbitAdmin(cf);
        Queue queue = new Queue("chatroom." + chatRoomName);
        admin.declareQueue(queue);

        String ROUTING_KEY = "chat.chatting." + chatRoomId;

        Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);;
        admin.declareBinding(binding);

        container.setQueueNames(queue.getName());
        container.setMessageListener(new MessageListenerAdapter(listener, "processMessage"));
    }
}

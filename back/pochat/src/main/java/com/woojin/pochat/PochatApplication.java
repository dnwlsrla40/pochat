package com.woojin.pochat;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.util.Listener;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@EnableJpaAuditing
@RequiredArgsConstructor
@SpringBootApplication
public class PochatApplication {

	private final ChatRoomRepository chatRoomRepository;
	private final TopicExchange exchange;

	private final SimpleMessageListenerContainer container;
	private final Listener listener;


	@PostConstruct
	public void queueBinding(){
		List<ChatRoom> chatRoomList = chatRoomRepository.findAll();

		CachingConnectionFactory cf = new CachingConnectionFactory("127.0.0.1", 5672);
		cf.setUsername("guest");
		cf.setPassword("guest");

		RabbitAdmin admin = new RabbitAdmin(cf);

		for (int i = 0; i < chatRoomList.size(); i++) {
			System.out.println("=========================queuename("+i+")" + chatRoomList.get(i).getQueueName());
			System.out.println("=========================routingKey("+i+")" + chatRoomList.get(i).getRoutingKey());
			Queue queue = new Queue(chatRoomList.get(i).getQueueName());
			admin.deleteQueue(queue.getName());
			admin.declareQueue(queue);

			Binding binding = BindingBuilder.bind(queue).to(exchange).with(chatRoomList.get(i).getRoutingKey());;
			admin.declareBinding(binding);

			container.addQueues(queue);
		}
		container.setMessageListener(new MessageListenerAdapter(listener, "processMessage"));
	}

	public static void main(String[] args) {
		SpringApplication.run(PochatApplication.class, args);
	}

}

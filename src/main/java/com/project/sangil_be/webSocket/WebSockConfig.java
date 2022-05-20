package com.project.sangil_be.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // emableSimpleBroker spring에서 제공하는 내장브로커 사용
        // 해당 파라미터를 perfix로 받고 있는 경우 그 메세지를 브로커가 처리하겠다
        // "/sub" 1대 다 로 메세지를 처리할때
        config.enableSimpleBroker("/sub");
        // setApplicationDestinationPrefixes
        // 바로 브로커로 가는 경우가 아니라 메세지가 가공이 필요할때 handler로 메세지가
        // 라우팅 되도록 하는 설정
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 인자로 들어가는는 "/ws-stomp"는 첫 websocket handshake를
        // 하기 위한 주소
        // cors 설정 과 sockJS설정을 할 수있다.
        // handler를 설정안해도 됨
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}

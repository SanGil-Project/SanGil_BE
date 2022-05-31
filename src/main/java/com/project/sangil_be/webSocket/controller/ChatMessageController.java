package com.project.sangil_be.webSocket.controller;

import com.project.sangil_be.webSocket.dto.MessageRequestDto;
import com.project.sangil_be.webSocket.dto.MessageResponseDto;
import com.project.sangil_be.webSocket.model.ChatMessage;
import com.project.sangil_be.webSocket.service.ChatMessageService;
import com.project.sangil_be.webSocket.service.ChatRoomJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomJoinService chatRoomJoinService;


    // stomp ws를 통해 해당 경로로 메세지가 들어왔을때 메시지의 "destination header"와 "messageMapping"에
    // 설정된 경로가 일치하는 "handler"를 찾고 처리
    // "configuration"에서 설정한 "sub"이라는 "prifix"과 합쳐서 "pub/chat/message"라는 "destination header"를 가진
    // 메세지들이 이 handler를 타게 된다.
    @MessageMapping("/chat/message")
    public void greeting(@RequestBody MessageRequestDto message) {
        System.out.println("chatHandler 에서 roomId : " + message.getRoomId());
        System.out.println("chatHandler 에서 message : " + message.getMessage());
        System.out.println("chatHandler 에서 userName : " + message.getSender());
        System.out.println("chatHandler 에서 type : " + message.getType());
        // 로그인 회원 정보를 들어온 메시지에 값 세팅
        // String username = jwtDecoder.decodeUsername(token);

        // 방입장 메세지 처리
        ChatMessage chatMessage = chatMessageService.saveMessage(message);

        List<MessageResponseDto> chatMessageList = new ArrayList<>();

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            System.out.println("====================enter 메세지가 들어왔습니다.================================");

            /** Todo
             *  1. 입장메세지
             *  2. [해당 유저가 chatRoomJoin에 존재하지 않을경우 ]
             *     -입장 시간 저장
             *  3. [해당 유저가 chatRoomJoin에 존재 할 경우]
             *     -해당 유저의 입장시간 이후의 메세지를 select 해서 list로 전달
             */
            if (chatRoomJoinService.userEnterChk(message).isPresent()) {
                System.out.println("enter 메세지가 들어왔을때--------방 입장 정보가 있을때 실행합니다.-----------------------------------");
                // 채팅방 내용
                chatMessageList = chatMessageService.chatMessageList(message);

                //   messagingTemplate.convertAndSend("/topic/greetings/" + message.getRoomId(),chatMessageList);
            } else {
                // 존재하지않는다면 입장시간 저장
                System.out.println("Enter가 들어왔는데================방 입장 정보가 없을때 실행합니다.=====================================");
                chatRoomJoinService.saveEnterTime(message);
                chatMessageList = chatMessageService.chatMessageList(message);
            }
            // 방을 구별해주기 위해서 @SendTo를 쓰지 않고 SimpMessageSendingOperations를 사용해서 방 구별을 해줄 수 있게 함 ex) "/topic/greetings+roomId"

        }else{
            System.out.println("=========================Talk message==============================");
            MessageResponseDto messageResponseDto = new MessageResponseDto(
                    chatMessage.getMessageId()
                    ,chatMessage.getMessage()
                    , chatMessage.getCreatedAt()
                    , chatMessage.getUser().getUserImgUrl()
                    , chatMessage.getUser().getNickname()
                    , chatMessage.getUser().getUserTitle()
                    , chatMessage.getChatroom().getChatRoomId()
                    , chatMessage.getMessageType());

            chatMessageList.add(messageResponseDto);
        }
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + message.getRoomId(),chatMessageList);
    }
}
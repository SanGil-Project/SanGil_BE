//package com.project.sangil_be.webSocket.service;
//
//import com.project.sangil_be.model.User;
//import com.project.sangil_be.webSocket.dto.MessageRequestDto;
//import com.project.sangil_be.webSocket.dto.MessageResponseDto;
//import com.project.sangil_be.webSocket.model.ChatMessage;
//import com.project.sangil_be.webSocket.model.ChatRoom;
//import com.project.sangil_be.webSocket.repository.ChatMessageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class ChatMessageService {
//
//    private final ChatMessageRepository chatMessageRepository;
//    private final ChatUtils chatUtils;
//
//    // messageType = "TALK" 채팅메세지 저장하기
//    public ChatMessage saveMessage(MessageRequestDto message) {
//
//        // 채팅방 가져오기
//        ChatRoom chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
//        // 유저 가져오기
//        User chatUser = chatUtils.selectOneUser(message.getSender());
//        // 메세지 전달 시간 저장
//        message.setCreatedAt(chatUtils.getCurrentTime());
//        // Message 저장 객체 만들기
//        ChatMessage chatMessage = new ChatMessage(chatRoom,chatUser,message);
//        // Message 객체 저장
//        return chatMessageRepository.save(chatMessage);
//    }
//    // 전체 메세지 가져오기
//    public List<MessageResponseDto> chatMessageList(MessageRequestDto message) {
//        // 채팅방 가져오기
//        ChatRoom chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
//        message.setType(ChatMessage.MessageType.TALK);
//        List<ChatMessage> selectMessageList = chatMessageRepository.findByChatroomAndMessageTypeOrderByCreatedAtAsc(chatRoom,message.getType());
//        List<MessageResponseDto> responseChatList = new ArrayList<>();
//
//        for(ChatMessage chatMessage : selectMessageList){
//            MessageResponseDto messageRequestDto = new MessageResponseDto(chatMessage);
//            responseChatList.add(messageRequestDto);
//        }
//        return responseChatList;
//    }
//
//    // messageType ="ENTER" 채팅 처리
//}

package com.project.sangil_be.webSocket.service;

import com.project.sangil_be.model.User;
import com.project.sangil_be.webSocket.dto.MessageRequestDto;
import com.project.sangil_be.webSocket.model.ChatRoom;
import com.project.sangil_be.webSocket.model.ChatRoomJoin;
import com.project.sangil_be.webSocket.repository.ChatRoomJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRoomJoinService {
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatUtils chatUtils;

    // 유저가 재입장인지 입장인지 판별
    public Optional<ChatRoomJoin> userEnterChk(MessageRequestDto message) {

        // 해당 방 가져오기
        ChatRoom chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
        // 유저 가져오기
        User user = chatUtils.selectOneUser(message.getSender());
        // 해당 유저가 이미 입장했을때 확인
        return chatRoomJoinRepository.findByChatRoomAndUser(chatRoom,user);

    }
    public void saveEnterTime(MessageRequestDto message) {
        // 해당 방 가져오기
        ChatRoom chatRoom = chatUtils.selectOneChatRoom(message.getRoomId());
        // 유저 가져오기
        User user = chatUtils.selectOneUser(message.getSender());
        // enterTime 저장
        message.setCreatedAt(chatUtils.getCurrentTime());

        ChatRoomJoin chatRoomJoin =new ChatRoomJoin(chatRoom, user, message);

        chatRoomJoinRepository.save(chatRoomJoin);
    }
}

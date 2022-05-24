package com.project.sangil_be.webSocket.service;

import com.project.sangil_be.webSocket.model.ChatRoom;
import com.project.sangil_be.webSocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(String name, Long partyId) {

        ChatRoom createChatRoom = new ChatRoom(name, partyId);
        return chatRoomRepository.save(createChatRoom);
    }
}
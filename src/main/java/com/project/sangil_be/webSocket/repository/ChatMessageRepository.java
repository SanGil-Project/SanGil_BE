package com.project.sangil_be.webSocket.repository;

import com.project.sangil_be.webSocket.model.ChatMessage;
import com.project.sangil_be.webSocket.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByChatroomAndMessageTypeOrderByCreatedAtAsc(ChatRoom chatRooms, ChatMessage.MessageType messageType);
}

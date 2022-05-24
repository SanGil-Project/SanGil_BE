package com.project.sangil_be.webSocket.dto;

import com.project.sangil_be.webSocket.model.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDto {
    private Long messageId;
    private String username;
    private Long roomId;
    private String message;
    private String createdAt;
    private String imageUrl;
    private String titleUrl;
    private ChatMessage.MessageType type;

    public MessageResponseDto(
            Long messageId,
            String message,
            String createdAt ,
            String imageUrl,
            String username,
            Long roomId,
            ChatMessage.MessageType messageType
    ) {
        this.messageId=messageId;
        this.message = message;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
        this.username = username;
        this.roomId= roomId;
        this.type =messageType;
    }

    public MessageResponseDto(ChatMessage chatMessage) {
        this.messageId = chatMessage.getMessageId();
        this.message = chatMessage.getMessage();
        this.username = chatMessage.getUser().getUsername();
        this.roomId = chatMessage.getChatroom().getChatRoomId();
        this.createdAt =chatMessage.getCreatedAt();
        this.type = chatMessage.getMessageType();
        this.imageUrl = chatMessage.getUser().getUserImgUrl();
    }
}
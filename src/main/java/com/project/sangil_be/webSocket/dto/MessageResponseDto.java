//package com.project.sangil_be.webSocket.dto;
//
//import com.project.sangil_be.webSocket.model.ChatMessage;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class MessageResponseDto {
//    private Long messageId;
//    private String nickname;
//    private Long roomId;
//    private String message;
//    private String createdAt;
//    private String userImageUrl;
//    private String userTitle;
//    private ChatMessage.MessageType type;
//
//    public MessageResponseDto(
//            Long messageId,
//            String message,
//            String createdAt ,
//            String imageUrl,
//            String nickname,
//            String userTitle,
//            Long roomId,
//            ChatMessage.MessageType messageType
//    ) {
//        this.messageId=messageId;
//        this.message = message;
//        this.createdAt = createdAt;
//        this.userImageUrl = imageUrl;
//        this.nickname = nickname;
//        this.userTitle = userTitle;
//        this.roomId= roomId;
//        this.type =messageType;
//    }
//
//    public MessageResponseDto(ChatMessage chatMessage) {
//        this.messageId = chatMessage.getMessageId();
//        this.message = chatMessage.getMessage();
//        this.createdAt =chatMessage.getCreatedAt();
//        this.userImageUrl = chatMessage.getUser().getUserImgUrl();
//        this.nickname = chatMessage.getUser().getNickname();
//        this.userTitle = chatMessage.getUser().getUserTitle();
//        this.roomId = chatMessage.getChatroom().getChatRoomId();
//        this.type = chatMessage.getMessageType();
//    }
//}
//package com.project.sangil_be.webSocket.model;
//
//import com.project.sangil_be.model.User;
//import com.project.sangil_be.webSocket.dto.MessageRequestDto;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class ChatRoomJoin {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long joinId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private ChatRoom chatRoom;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;
//
//    @Column
//    private String enterTime;
//
//    public ChatRoomJoin(ChatRoom chatRoom , User user, MessageRequestDto message) {
//        this.chatRoom=chatRoom;
//        this.user=user;
//        this.enterTime = message.getCreatedAt();
//    }
//
//}
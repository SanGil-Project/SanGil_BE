package com.project.sangil_be.webSocket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id
    private Long chatRoomId;
    @Column
    private String chatRoomName;

    public ChatRoom(String name, Long partyId) {
        this.chatRoomId = partyId;
        this.chatRoomName= name;
    }

    public ChatRoom(Long chatRoomId,String chatRoomName) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName= chatRoomName;
    }


}
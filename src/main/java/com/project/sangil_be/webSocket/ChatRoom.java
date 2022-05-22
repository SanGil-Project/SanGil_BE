package com.project.sangil_be.webSocket;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private Long roomId;
    private String name;

    public static ChatRoom create(String name,Long partyId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = partyId;
        chatRoom.name = name;
        return chatRoom;
    }
}

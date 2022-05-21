package com.project.sangil_be.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> chatRoomList() {
        return chatRoomRepository.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/rooms/{partyId}")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam("name") String name, @PathVariable Long partyId) {
        return chatRoomRepository.createChatRoom(name, partyId);
    }

    // 특정 채팅방 조회
    @GetMapping("/rooms/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable Long roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
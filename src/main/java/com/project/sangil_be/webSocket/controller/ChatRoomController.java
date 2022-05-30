//package com.project.sangil_be.webSocket.controller;
//
//import com.project.sangil_be.webSocket.model.ChatRoom;
//import com.project.sangil_be.webSocket.service.ChatRoomService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class ChatRoomController {
//    private final ChatRoomService chatRoomService;
//
//    // 채팅방 생성 (react_local:3000)
//    @PostMapping("/chat/rooms")
//    public ChatRoom createChatRoom(@RequestParam String name, @RequestParam Long partyId) {
//        log.info("생성된 채팅방 이름 = {}", name);
//        return chatRoomService.createChatRoom(name, partyId);
//    }
//}
//

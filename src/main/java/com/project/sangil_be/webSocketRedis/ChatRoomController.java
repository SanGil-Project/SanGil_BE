//package com.project.sangil_be.webSocketRedis;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/chat")
//public class ChatRoomController {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    // 모든 채팅방 목록 반환
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> chatRoomList() {
//        return chatRoomRepository.findAllRoom();
//    }
//
//    // 채팅방 생성 // 파티 타이틀과 roomID
//    @PostMapping("/rooms")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestParam("name") String name,@RequestParam Long partyId) {
//        return chatRoomRepository.createChatRoom(name,partyId);
//    }
//
//    // 특정 채팅방
////    @GetMapping("/rooms/{roomId}")
////    @ResponseBody
////    public ChatRoom roomInfo(@PathVariable Long roomId) {
////        return chatRoomRepository.findRoomById(roomId);
////    }
//
//    @GetMapping("/rooms/{roomId}")
//    @ResponseBody
//    public List<ChatMessage> findChat(@PathVariable Long roomId) {
//          return chatRoomRepository.findChatById(String.valueOf(roomId));
//    }
//
//}
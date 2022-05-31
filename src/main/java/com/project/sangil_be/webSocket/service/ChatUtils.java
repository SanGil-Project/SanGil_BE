package com.project.sangil_be.webSocket.service;

import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.UserRepository;
import com.project.sangil_be.webSocket.model.ChatRoom;
import com.project.sangil_be.webSocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RequiredArgsConstructor
@Service
public class ChatUtils {

    public final ChatRoomRepository chatRoomRepository;
    public final UserRepository userRepository;

    // 채팅방 가져오기
    public ChatRoom selectOneChatRoom(Long roomId){
        return chatRoomRepository.findByChatRoomId(roomId).orElseThrow(
                ()-> new NullPointerException("해당 채팅방이 존재하지 않습니다.")
        );
    }

    // 유저 가져오기
    public User selectOneUser(String sender){
        return userRepository.findByNickname(sender).orElseThrow(
                ()-> new NullPointerException("해당 회원이 존재하지 않습니다.")
        );
    }

    // 메시지 생성 시간 계산
    public String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return sdf.format(date);
    }
}
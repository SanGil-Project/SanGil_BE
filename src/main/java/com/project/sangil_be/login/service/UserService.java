package com.project.sangil_be.login.service;

import com.project.sangil_be.login.dto.SignUpResponseDto;
import com.project.sangil_be.login.dto.SignUpRequestDto;
import com.project.sangil_be.model.User;
import com.project.sangil_be.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignUpResponseDto registerUser(SignUpRequestDto requestDto) {
        Boolean result = true;
        String username = requestDto.getUsername();

        Optional<User> foundname = userRepository.findByUsername(username);

        if (foundname.isPresent()) {
            result = false;
            return new SignUpResponseDto(result);
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = "없음";
        String userImageUrl = "없음";
        String userTitle = "없음";
        String userTitleImgUrl="없음";
        String socialId = "0";

        User user = new User(username, socialId,password, nickname, userImageUrl, userTitle,userTitleImgUrl);
        userRepository.save(user);

        SignUpResponseDto responseDto = new SignUpResponseDto(result);

        return responseDto;

    }

}
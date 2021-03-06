package com.project.sangil_be.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.sangil_be.login.dto.SignUpResponseDto;
import com.project.sangil_be.login.dto.SignUpRequestDto;
import com.project.sangil_be.login.dto.SocialLoginDto;
import com.project.sangil_be.login.dto.UserResponseDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.login.service.GoogleUserService;
import com.project.sangil_be.login.service.KakaoUserService;
import com.project.sangil_be.login.service.NaverUserService;
import com.project.sangil_be.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;
    private final NaverUserService naverUserService;
    private final UserService userService;

    // 카카오 로그인
    @GetMapping("/user/kakao/callback")
    public SocialLoginDto kakaoLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(code, response);
    }

    // 구글 로그인
    @GetMapping("/user/google/callback")
    public void googleLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws JsonProcessingException {
        googleUserService.googleLogin(code, response);
    }

    // 네이버 로그인
    @GetMapping("/user/naver/callback")
    public void naverLogin(
            @RequestParam String code,
            @RequestParam String state,
            HttpServletResponse response
    ) throws JsonProcessingException{
        naverUserService.naverLogin(code, state, response);
    }

    // 회원가입
    @PostMapping("api/user/signup")
    public SignUpResponseDto signup(@RequestBody SignUpRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    // 로그인 체크
    @GetMapping("/user/loginCheck")
    public UserResponseDto isLogin(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails);
        return new UserResponseDto(userDetails);
    }

}

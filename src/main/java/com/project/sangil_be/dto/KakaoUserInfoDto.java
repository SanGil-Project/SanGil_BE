package com.project.sangil_be.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfoDto {
    private String username;
    private String nickname;

    public KakaoUserInfoDto(String username, String nickname) {
        this.username=username;
        this.nickname=nickname;
    }


}
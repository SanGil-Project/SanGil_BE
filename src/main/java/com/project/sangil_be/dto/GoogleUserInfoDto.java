package com.project.sangil_be.dto;

import lombok.Getter;

@Getter
public class GoogleUserInfoDto {
    private String username;
    private String nickname;

    public GoogleUserInfoDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}

package com.project.sangil_be.party.dto;

import com.project.sangil_be.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartymemberDto {
    private String nickname;
    private String userImgUrl;
    private String userTitle;


    public PartymemberDto(User user) {
        this.nickname = user.getNickname();
        this.userImgUrl = user.getUserImgUrl();
        this.userTitle = user.getUserTitle();
    }
}

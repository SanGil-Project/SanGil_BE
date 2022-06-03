package com.project.sangil_be.mypage.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserTitleResponseDto {
    private List<UserTitleDto> userTitleList;
    private List<TitleDto> titleDtoList;

    public UserTitleResponseDto(List<UserTitleDto> userTitleDtoList, List<TitleDto> titleDtoList) {
        this.userTitleList = userTitleDtoList;
        this.titleDtoList = titleDtoList;
    }
}

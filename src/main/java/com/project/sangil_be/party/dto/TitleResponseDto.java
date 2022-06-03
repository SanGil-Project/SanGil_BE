package com.project.sangil_be.party.dto;

import com.project.sangil_be.mypage.dto.TitleDto;
import lombok.Data;

import java.util.List;

@Data
public class TitleResponseDto {
    private List<TitleDto> titleDtoList;
    private String result;

    public TitleResponseDto(List<TitleDto> titleDtoList, String msg) {
        this.titleDtoList = titleDtoList;
        this.result = msg;
    }
}

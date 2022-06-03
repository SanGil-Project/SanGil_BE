package com.project.sangil_be.feed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoodCheckResponseDto {
    boolean goodStatus;
    Integer goodCnt;

    public GoodCheckResponseDto(boolean goodStatus, Integer goodCnt) {
        this.goodStatus = goodStatus;
        this.goodCnt=goodCnt;
    }
}

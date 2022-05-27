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

    public GoodCheckResponseDto(Integer goodCnt, boolean goodStatus) {
        this.goodCnt=goodCnt;
        this.goodStatus = goodStatus;
    }
}

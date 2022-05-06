package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class FeedListResponseDto {
    private List<FeedResponseDto> feedList;
    private int totalPage;
    private int currentPage;

    public FeedListResponseDto(Page<FeedResponseDto> feedResponseDtoPage) {
        this.feedList = feedResponseDtoPage.getContent();
        this.totalPage = feedResponseDtoPage.getTotalPages();
        this.currentPage = feedResponseDtoPage.getNumber();
    }
}

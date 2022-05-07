package com.project.sangil_be.dto;

import lombok.Getter;
<<<<<<< HEAD
import lombok.Setter;
=======
>>>>>>> de1fe887814e7019225612f2f344d379f813899c
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class FeedListResponseDto {
    private List<FeedResponseDto> feedList;
    private int totalPage;
    private int currentPage;

    public FeedListResponseDto(Page<FeedResponseDto> page) {
        this.feedList=page.getContent();
        this.totalPage=page.getTotalPages();
        this.currentPage=page.getNumber();
    }
}

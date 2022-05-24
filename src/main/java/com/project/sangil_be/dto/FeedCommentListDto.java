package com.project.sangil_be.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class FeedCommentListDto {
    private List<CommentResponseDto> commentResponseDtos;
    private int totalPage;
    private int currentPage;

    public FeedCommentListDto(Page<CommentResponseDto> page) {
        this.commentResponseDtos = page.getContent();
        this.totalPage = page.getTotalPages();
        this.currentPage = page.getNumber();
    }
}

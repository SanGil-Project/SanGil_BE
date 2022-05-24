package com.project.sangil_be.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class BookMarkDto {
    private List<BookMarkResponseDto> bookMarkResponseDtos;
    private int totalPage;
    private int currentPage;

    public BookMarkDto(Page<BookMarkResponseDto> bookMarkResponseDtos) {
        this.bookMarkResponseDtos = bookMarkResponseDtos.getContent();
        this.totalPage = bookMarkResponseDtos.getTotalPages();
        this.currentPage = bookMarkResponseDtos.getNumber();
    }
}

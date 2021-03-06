package com.project.sangil_be.mountain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class SearchAfterDto {
    private List<SearchDto> searchList;
    private int totalPage;
    private int currentPage;

    public SearchAfterDto(Page<SearchDto> searchMountain) {
        this.searchList = searchMountain.getContent();
        this.totalPage = searchMountain.getTotalPages();
        this.currentPage = searchMountain.getNumber();
    }
}

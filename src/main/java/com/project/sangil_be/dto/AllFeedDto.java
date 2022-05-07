package com.project.sangil_be.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AllFeedDto {

    private Long userId;

    private String username;

    private String userImageUrl;

    private String feedImageUrl;

    private String feedContent;

    private LocalDateTime createdAt;

}

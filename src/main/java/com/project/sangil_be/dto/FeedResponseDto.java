package com.project.sangil_be.dto;

import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedResponseDto {

    private Long userId;

    private String username;

    private String userImageUrl;

    private String feedImageUrl;

    private String feedContent;

    private LocalDateTime createdAt;

    private Long goodCnt;

    private boolean result;

    public FeedResponseDto(User user, Feed feed, long l) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.userImageUrl = user.getUserImgUrl();
        this.feedImageUrl = feed.getFeedImgUrl();
        this.feedContent = feed.getFeedContent();
        this.createdAt = feed.getCreatedAt();
        this.goodCnt = l;
    }
}

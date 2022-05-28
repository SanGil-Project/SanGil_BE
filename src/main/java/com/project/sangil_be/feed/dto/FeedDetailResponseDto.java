package com.project.sangil_be.feed.dto;

import com.project.sangil_be.model.Feed;
import lombok.Data;

@Data
public class FeedDetailResponseDto {
    private Long userId;
    private String nickname;
    private String userTitle;
    private String userImageUrl;
    private String feedImageUrl;
    private String feedContent;
    private String beforeTime;
    private Integer goodCnt;
    private Boolean goodStatus;
    private FeedCommentListDto feedCommentListDto;

    public FeedDetailResponseDto(Feed feed, Integer goodCnt, boolean goodStatus, FeedCommentListDto feedCommentListDto, String feedBeforeTime) {
        this.userId=feed.getUser().getUserId();
        this.nickname=feed.getUser().getNickname();
        this.userTitle=feed.getUser().getUserTitle();
        this.userImageUrl=feed.getUser().getUserImgUrl();
        this.feedImageUrl=feed.getFeedImgUrl();
        this.feedContent=feed.getFeedContent();
        this.beforeTime = feedBeforeTime;
        this.goodCnt=goodCnt;
        this.goodStatus=goodStatus;
        this.feedCommentListDto=feedCommentListDto;
    }
}

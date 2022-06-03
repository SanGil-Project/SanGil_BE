package com.project.sangil_be.feed.dto;

import com.project.sangil_be.mypage.dto.TitleDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedResponseDto {
    private Long userId;
    private Long feedId;
    private String nickname;
    private String userTitle;
    private String userImgUrl;
    private String feedImgUrl;
    private String feedContent;
    private LocalDateTime createdAt;
    private Integer goodCnt;
    private Boolean goodStatus;
    private Long commentCnt;
    private List<FeedCommentResDto> commentList;
    private List<TitleDto> titleDtoList;
    private String beforeTime;

    public FeedResponseDto(User user, Feed feed, int goodCnt, String beforeTime) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.userImgUrl = user.getUserImgUrl();
        this.feedImgUrl = feed.getFeedImgUrl();
        this.feedContent = feed.getFeedContent();
        this.beforeTime = beforeTime;
        this.goodCnt = goodCnt;
    }

    public FeedResponseDto(Feed feed, int goodCnt, boolean goodStatus, String beforeTime) {
        this.feedId = feed.getFeedId();
        this.nickname = feed.getUser().getNickname();
        this.userTitle = feed.getUser().getUserTitle();
        this.userImgUrl = feed.getUser().getUserImgUrl();
        this.feedImgUrl = feed.getFeedImgUrl();
        this.feedContent = feed.getFeedContent();
        this.createdAt = feed.getCreatedAt();
        this.goodCnt = goodCnt;
        this.goodStatus = goodStatus;
        this.beforeTime = beforeTime;
    }


    public FeedResponseDto(Feed feed, int goodCnt, boolean goodStatus, String beforeTime, Long commentCnt, List<FeedCommentResDto> feedCommentResDtos) {
        this.userId = feed.getUser().getUserId();
        this.feedId = feed.getFeedId();
        this.nickname = feed.getUser().getNickname();
        this.userTitle = feed.getUser().getUserTitle();
        this.userImgUrl = feed.getUser().getUserImgUrl();
        this.feedImgUrl = feed.getFeedImgUrl();
        this.feedContent = feed.getFeedContent();
        this.beforeTime = beforeTime;
        this.goodCnt = goodCnt;
        this.goodStatus = goodStatus;
        this.commentCnt = commentCnt;
        this.commentList = feedCommentResDtos;
    }

    public FeedResponseDto(Feed feeds) {
        this.feedId=feeds.getFeedId();
        this.feedImgUrl=feeds.getFeedImgUrl();
    }

    public FeedResponseDto(Feed feed, int goodCnt, boolean goodStatus, List<TitleDto> titleDtoList) {
        this.feedId = feed.getFeedId();
        this.feedImgUrl = feed.getFeedImgUrl();
        this.goodCnt = goodCnt;
        this.goodStatus = goodStatus;
        this. titleDtoList = titleDtoList;
    }


}

package com.project.sangil_be.dto;

import com.project.sangil_be.model.FeedComment;
import lombok.Data;

@Data
public class FeedCommentResDto {
    private Long feedCommentId;
    private Long userId;
    private String nickname;
    private String userTitle;
    private String userImageUrl;
    private String feedComment;
    private String beforeTime;

    public FeedCommentResDto(FeedComment feedComment) {
        this.feedCommentId = feedComment.getFeedCommentId();
        this.userId = feedComment.getUser().getUserId();
        this.nickname = feedComment.getUser().getNickname();
        this.userTitle = feedComment.getUser().getUserTitle();
        this.userImageUrl = feedComment.getUser().getUserImgUrl();
    }

}

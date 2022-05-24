package com.project.sangil_be.dto;

import com.project.sangil_be.model.FeedComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long userId;
    private Long commentId;
    private String nickname;
    private String userTitle;
    private String userImgUrl;
    private String feedComment;
    private LocalDateTime createdAt;

    public CommentResponseDto(FeedComment feedComment) {
        this.userId=feedComment.getUser().getUserId();
        this.commentId=feedComment.getFeedCommentId();
        this.nickname=feedComment.getUser().getNickname();
        this.userTitle=feedComment.getUser().getUserTitle();
        this.userImgUrl=feedComment.getUser().getUserImgUrl();
        this.feedComment=feedComment.getFeedComment();
        this.createdAt=feedComment.getCreatedAt();
    }
}

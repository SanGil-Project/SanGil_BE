package com.project.sangil_be.dto;

import com.project.sangil_be.model.FeedComment;
import lombok.Data;

@Data
public class FeedCommentResDto {
    private Long feedCommentId;
    private String nickname;

    public FeedCommentResDto(FeedComment feedComment) {
        this.feedCommentId = feedComment.getFeedCommentId();
        this.nickname = feedComment.getUser().getNickname();
    }

}

package com.project.sangil_be.dto;

import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentListDto {
    private Long mountainCommentId;
    private String mountainComment;
    private Long userId;
    private String nickname;
    private String userTitle;
    private int star;
    private LocalDateTime createdAt;

    public CommentListDto(MountainComment mountainComment, User user ) {
        this.mountainCommentId = mountainComment.getMountainCommentId();
        this.mountainComment = mountainComment.getMountainComment();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.userTitle = user.getUserTitle();
        this.star = mountainComment.getStar();
        this.createdAt = mountainComment.getCreatedAt();
    }

}

package com.project.sangil_be.dto;

import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.User;
import lombok.Getter;

@Getter
public class CommentListDto {
    private Long mountainCommentId;
    private String mountainComment;
    private String username;
    private String userTitle;
    private int star;

    public CommentListDto(MountainComment mountainComment, User user) {
        this.mountainCommentId = mountainComment.getMountainCommentId();
        this.mountainComment = mountainComment.getMountainComment();
        this.username = user.getUsername();
        this.userTitle = user.getUserTitle();
        this.star = mountainComment.getStar();
    }
}

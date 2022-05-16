package com.project.sangil_be.dto;

import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MCommentResponseDto {

    private Long mountainCommentId;
    private String mountainComment;
    private String userTitle;
    private Long userId;
    private String nickname;
    private int star;
    private String msg;
    private LocalDateTime createdAt;

    public MCommentResponseDto( MountainComment mountainComment, UserDetailsImpl userDetails, String msg) {
        this.mountainCommentId = mountainComment.getMountainCommentId();
        this.mountainComment = mountainComment.getMountainComment();
        this.userTitle = userDetails.getUser().getUserTitle();
        this.userId = userDetails.getUser().getUserId();
        this.nickname = userDetails.getNickname();
        this.star = mountainComment.getStar();
        this.msg = msg;
        this.createdAt = mountainComment.getCreatedAt();
    }

    public MCommentResponseDto(MountainComment mountainComment, UserDetailsImpl userDetails) {
        this.mountainCommentId = mountainComment.getMountainCommentId();
        this.mountainComment = mountainComment.getMountainComment();
        this.userId = userDetails.getUser().getUserId();
        this.userTitle = userDetails.getUser().getUserTitle();
        this.nickname = userDetails.getUser().getNickname();
        this.star = mountainComment.getStar();
    }
}


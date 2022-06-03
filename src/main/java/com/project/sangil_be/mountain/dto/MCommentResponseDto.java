package com.project.sangil_be.mountain.dto;

import com.project.sangil_be.mypage.dto.TitleDto;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<TitleDto> titleDtoList;
    private String beforeTime;

    public MCommentResponseDto( MountainComment mountainComment, UserDetailsImpl userDetails, String msg, List<TitleDto> titleDtoList, String beforeTime) {
        this.mountainCommentId = mountainComment.getMountainCommentId();
        this.mountainComment = mountainComment.getMountainComment();
        this.userTitle = userDetails.getUser().getUserTitle();
        this.nickname = userDetails.getNickname();
        this.userId = mountainComment.getUserId();
        this.star = mountainComment.getStar();
        this.msg = msg;
        this.beforeTime=beforeTime;
        this.titleDtoList = titleDtoList;
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


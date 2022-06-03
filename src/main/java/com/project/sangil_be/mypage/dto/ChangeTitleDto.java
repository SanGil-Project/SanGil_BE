package com.project.sangil_be.mypage.dto;

import com.project.sangil_be.model.UserTitle;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;

@Getter
public class ChangeTitleDto {
    private Long userId;
    private String userTitle;
    private String userTitleImgUrl;
    private String beforeTitleImgUrl;

    public ChangeTitleDto(UserDetailsImpl userDetails, UserTitle usertitle, UserTitle userTitle2) {
        this.userId=userDetails.getUser().getUserId();
        this.userTitle= usertitle.getUserTitle();
        this.userTitleImgUrl = usertitle.getCTitleImgUrl();
        this.beforeTitleImgUrl = userTitle2.getBTitleImgUrl();
    }
}

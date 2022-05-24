package com.project.sangil_be.dto;

import com.project.sangil_be.model.UserTitle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTitleDto {
    private String userTitle;
    private String userTitleImgUrl;
    private Boolean have;

    public UserTitleDto(UserTitle userTitle, String TitleImgUrl, Boolean aBoolean) {
        this.userTitle=userTitle.getUserTitle();
        this.userTitleImgUrl=TitleImgUrl;
        this.have=aBoolean;
    }
}

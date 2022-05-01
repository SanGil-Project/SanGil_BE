package com.project.sangil_be.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyCommentDto {
    private Long partyCommentId;
    private String partyComment;
    private String userTitle;
    private String username;

    public PartyCommentDto(Long partyCommentId, String partyComment, String userTitle, String username) {
        this.partyCommentId = partyCommentId;
        this.partyComment = partyComment;
        this.userTitle = userTitle;
        this.username = username;
    }
}

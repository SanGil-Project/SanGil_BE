package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyCommentRequestDto {
    private Long partyId;
    private String partyComment;
    private String username;
    private String userTitle;
}

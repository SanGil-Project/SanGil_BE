package com.project.sangil_be.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlanListDto {
    private String title;
    private LocalDateTime createdAt;
    private int maxPoeple;
    private int curPoeple;
    private String partyDate;
    private String msg;

    public PlanListDto(Party party,String msg) {
        this.title = party.getTitle();
        this.createdAt = party.getCreatedAt();
        this.maxPoeple = party.getMaxPeople();
        this.curPoeple = party.getCurPeople();
        this.partyDate = party.getPartyDate();
        this.msg = msg;
    }

}
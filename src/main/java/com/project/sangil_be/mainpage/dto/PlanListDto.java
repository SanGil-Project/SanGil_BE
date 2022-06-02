package com.project.sangil_be.mainpage.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PlanListDto {
    private Long partyId;
    private String title;
    private int maxPeople;
    private int currentPeople;
    private String partyDate;
    private String msg;

    public PlanListDto(Party party,String msg) {
        this.partyId = party.getPartyId();
        this.title = party.getTitle();
        this.maxPeople = party.getMaxPeople();
        this.currentPeople = party.getCurPeople();
        this.partyDate = party.getPartyDate();
        this.msg = msg;
    }

}

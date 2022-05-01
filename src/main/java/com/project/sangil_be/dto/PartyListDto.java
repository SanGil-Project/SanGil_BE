package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyListDto {
    private Long partyId;
    private String title;
    private String partyContent;
    private String mountain;
    private String address;
    private String partyDate;
    private int maxPeople;
    private int curPeople;

    public PartyListDto(Long partyId, String title, String partyContent, String mountain, String address, String partyDate, int maxPeople, int curPeople) {
        this.partyId = partyId;
        this.title = title;
        this.partyContent = partyContent;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
    }
}

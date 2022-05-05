package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PartyListDto {
    private Long partyId;
    private String username;
    private String title;
    private String partyContent;
    private String mountain;
    private String address;
    private String partyDate;
    private String partyTime;
    private int maxPeople;
    private int curPeople;
    private boolean completed;
    private LocalDateTime createdAt;

    public PartyListDto(Long partyId, String username, String title, String partyContent,
                        String mountain, String address, String partyDate, String partyTime,
                        int maxPeople, int curPeople, boolean completed, LocalDateTime createdAt) {
        this.partyId = partyId;
        this.username = username;
        this.title = title;
        this.partyContent = partyContent;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.partyTime = partyTime;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
        this.completed = completed;
        this.createdAt = createdAt;
    }
}

package com.project.sangil_be.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class PartyDetailDto {
    private Long partyId;
    private String username;
    private String userImgUrl;
    private String userTitle;
    private String title;
    private String mountain;
    private String address;
    private LocalDate partyDate;
    private LocalTime partyTime;
    private String partyContent;
    private int maxPeople;
    private int curPeople;
    private LocalDateTime createdAt;
    private List<PartymemberDto> partymemberDto;

    public PartyDetailDto(Long partyId, String username, String userImageUrl,
                          String userTitle, String title, String mountain,
                          String address, LocalDate partyDate, int maxPeople,
                          int curPeople, String partyContent, LocalDateTime createdAt) {

        this.partyId = partyId;
        this.username = username;
        this.userImgUrl = userImageUrl;
        this.userTitle = userTitle;
        this.title = title;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
        this.partyContent = partyContent;
        this.createdAt = createdAt;
    }

    public PartyDetailDto(Party party, List<PartymemberDto> partymemberDto) {
        this.partyId = party.getPartyId();
        this.username = party.getUser().getUsername();
        this.userImgUrl = party.getUser().getUserImgUrl();
        this.userTitle = party.getUser().getUserTitle();
        this.title = party.getTitle();
        this.mountain = party.getMountain();
        this.address = party.getAddress();
        this.partyDate = party.getPartyDate();
        this.partyTime = party.getPartyTime();
        this.partyContent = party.getPartyContent();
        this.maxPeople = party.getMaxPeople();
        this.curPeople = party.getCurPeople();
        this.createdAt = party.getCreatedAt();
        this.partymemberDto = partymemberDto;
    }
}

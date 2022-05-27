package com.project.sangil_be.party.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PartyDetailDto {
    private Long partyId;
    private String nickname;
    private String userImgUrl;
    private String userTitle;
    private String title;
    private String mountain;
    private String address;
    private String partyDate;
    private String partyTime;
    private String partyContent;
    private int maxPeople;
    private int curPeople;
    private LocalDateTime createdAt;
    private String beforeTime;
    private List<PartymemberDto> partymemberDto;

    public PartyDetailDto(Party party, List<PartymemberDto> partymemberDto, String beforeTime) {
        this.partyId = party.getPartyId();
        this.nickname = party.getUser().getNickname();
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
        this.beforeTime = beforeTime;
        this.partymemberDto = partymemberDto;
    }

    public PartyDetailDto(Party party) {
        this.partyId = party.getPartyId();
        this.nickname = party.getUser().getNickname();
        this.userImgUrl = party.getUser().getUserImgUrl();
        this.userTitle = party.getUser().getUserTitle();
        this.title = party.getTitle();
        this.mountain = party.getMountain();
        this.address = party.getAddress();
        this.partyDate = party.getPartyDate();
        this.maxPeople = party.getMaxPeople();
        this.curPeople = party.getCurPeople();
        this.partyContent = party.getPartyContent();
        this.createdAt = party.getCreatedAt();
    }
}

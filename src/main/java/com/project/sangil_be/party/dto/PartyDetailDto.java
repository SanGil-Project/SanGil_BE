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
    private String mountainAddress;
    private String partyDate;
    private String partyTime;
    private String partyContent;
    private int maxPeople;
    private int currentPeople;
    private boolean completed;
    private LocalDateTime createdAt;
    private String beforeTime;
    private List<PartymemberDto> partyMember;

    public PartyDetailDto(Party party, List<PartymemberDto> partymemberDto, boolean completed, String beforeTime) {
        this.partyId = party.getPartyId();
        this.nickname = party.getUser().getNickname();
        this.userImgUrl = party.getUser().getUserImgUrl();
        this.userTitle = party.getUser().getUserTitle();
        this.title = party.getTitle();
        this.mountain = party.getMountain();
        this.mountainAddress = party.getAddress();
        this.partyDate = party.getPartyDate();
        this.partyTime = party.getPartyTime();
        this.partyContent = party.getPartyContent();
        this.maxPeople = party.getMaxPeople();
        this.currentPeople = party.getCurPeople();
        this.beforeTime = beforeTime;
        this.completed = completed;
        this.partyMember = partymemberDto;
    }

    public PartyDetailDto(Party party, String beforeTime) {
        this.partyId = party.getPartyId();
        this.nickname = party.getUser().getNickname();
        this.userTitle = party.getUser().getUserTitle();
        this.title = party.getTitle();
        this.mountain = party.getMountain();
        this.mountainAddress = party.getAddress();
        this.partyDate = party.getPartyDate();
        this.partyTime = party.getPartyTime();
        this.maxPeople = party.getMaxPeople();
        this.currentPeople = party.getCurPeople();
        this.partyContent = party.getPartyContent();
        this.completed = party.getCompleted();
        this.beforeTime = beforeTime;
    }
}

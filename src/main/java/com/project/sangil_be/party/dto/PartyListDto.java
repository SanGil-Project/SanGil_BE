package com.project.sangil_be.party.dto;

import com.project.sangil_be.model.Party;
import com.project.sangil_be.mypage.dto.TitleDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PartyListDto {
    private Long partyId;
    private String nickname;
    private String userTitle;
    private String title;
    private String partyContent;
    private String mountain;
    private String mountainAddress;
    private String partyDate;
    private String partyTime;
    private int maxPeople;
    private int currentPeople;
    private boolean completed;
    private LocalDateTime createdAt;
    private String beforeTime;
    private List<TitleDto> titleDtoList;

    public PartyListDto(Party party, boolean completed, String beforeTime) {
        this.partyId = party.getPartyId();
        this.nickname = party.getUser().getNickname();
        this.title = party.getTitle();
        this.mountain = party.getMountain();
        this.mountainAddress = party.getAddress();
        this.partyDate = party.getPartyDate();
        this.partyTime = party.getPartyTime();
        this.maxPeople = party.getMaxPeople();
        this.currentPeople = party.getCurPeople();
        this.completed = completed;
        this.beforeTime = beforeTime;
        this.userTitle = party.getUser().getUserTitle();
    }

    public PartyListDto(Party party, boolean completed, List<TitleDto> titleDtoList, String beforeTime) {
        this.partyId = party.getPartyId();
        this.nickname = party.getUser().getNickname();
        this.title = party.getTitle();
        this.partyContent = party.getPartyContent();
        this.mountain = party.getMountain();
        this.mountainAddress = party.getAddress();
        this.partyDate = party.getPartyDate();
        this.partyTime = party.getPartyTime();
        this.maxPeople = party.getMaxPeople();
        this.currentPeople = party.getCurPeople();
        this.completed = completed;
        this.beforeTime = beforeTime;
        this.userTitle = party.getUser().getUserTitle();
        this.titleDtoList = titleDtoList;
    }

    public PartyListDto(Long partyId, String nickname, String title, String mountain, String address, String partyDate, String partyTime, int maxPeople, int curPeople, LocalDateTime createdAt) {
        this.partyId = partyId;
        this.nickname = nickname;
        this.title = title;
        this.mountain = mountain;
        this.mountainAddress = address;
        this.partyDate = partyDate;
        this.partyTime = partyTime;
        this.maxPeople = maxPeople;
        this.currentPeople = curPeople;
        this.createdAt = createdAt;
    }
}

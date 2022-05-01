package com.project.sangil_be.dto;

import com.project.sangil_be.model.Party;
import com.project.sangil_be.model.PartyComment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartyDetailDto {
    private Long partyId;
    private String title;
    private String mountain;
    private String address;
    private String partyDate;
    private String partyContent;
    private int maxPeople;
    private int curPeople;
    private List<PartyComment> partyComments;
    private int totalPage;
    private int currentPage;

    public PartyDetailDto(Long partyId, String title, String mountain, String address, String partyDate, int maxPeople, int curPeople, String partyContent, List<PartyComment> partyComments) {
        this.partyId = partyId;
        this.title = title;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
        this.partyContent = partyContent;
        this.partyComments = partyComments;
    }
}

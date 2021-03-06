package com.project.sangil_be.party.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PartyRequestDto {
    private String title;
    private String mountain;
    private String mountainAddress;
    private String partyDate;
    private String partyTime;
    private int maxPeople;
    private String partyContent;
}

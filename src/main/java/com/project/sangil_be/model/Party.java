package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(nullable = false)
    private String partyContent;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String mountain;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String partyDate;

    @Column(nullable = false)
    private int maxPeople;

    @Column(nullable = false)
    private int curPeople;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "party", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PartyComment> PartyCommens;
}

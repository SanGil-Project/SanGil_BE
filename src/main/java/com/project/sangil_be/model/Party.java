package com.project.sangil_be.model;

import com.project.sangil_be.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Party extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String partyContent;

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

    @Column(nullable = false)
    private String partyTime;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Party(String title, String mountain, String address, String partyDate,
                 String partyTime, int maxPeople, int curPeople, String partyContent,
                 boolean completed, User user) {
        this.title = title;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.partyTime = partyTime;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
        this.partyContent = partyContent;
        this.completed = completed;
        this.user = user;
    }

    public void update(String partyDate, String partyTime, int maxPeople, String partyContent) {
        this.partyDate = partyDate;
        this.partyTime = partyTime;
        this.maxPeople = maxPeople;
        this.partyContent = partyContent;
    }

    public void updateCurpeople(int result) {
        this.curPeople = result;
    }
}

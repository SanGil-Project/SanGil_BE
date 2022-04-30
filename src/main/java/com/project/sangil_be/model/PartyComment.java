package com.project.sangil_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sangil_be.utils.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PartyComment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyCommentId;

    @Column(nullable = false)
    private String partyComment;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String userTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partyId")
    @JsonIgnore
    private Party party;

    public PartyComment (Party party, String partyComment, String userTitle, String username) {
        this.party = party;
        this.partyComment = partyComment;
        this.userTitle = userTitle;
        this.username = username;
    }

    public void update(String partyComment, String userTitle, String username) {
        this.partyComment = partyComment;
        this.userTitle = userTitle;
        this.username = username;
    }
}

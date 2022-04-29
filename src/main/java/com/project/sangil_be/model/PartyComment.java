package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PartyComment {
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
    private Party party;

}

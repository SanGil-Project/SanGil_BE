package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTitleId;

    @Column(nullable = false)
    private String userTitle;

    @Column(nullable = false)
    private String bTitleImgUrl;

    @Column(nullable = false)
    private String cTitleImgUrl;

    @Column(nullable = false)
    private String qTitleImgUrl;


}

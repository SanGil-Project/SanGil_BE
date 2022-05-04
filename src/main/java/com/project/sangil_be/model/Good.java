package com.project.sangil_be.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodId;

    @Column(nullable = false)
    private Long feedId;

    @Column(nullable = false)
    private Long userId;

}

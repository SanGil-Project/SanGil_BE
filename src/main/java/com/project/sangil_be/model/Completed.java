package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Completed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completeId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long mountain100Id;
}

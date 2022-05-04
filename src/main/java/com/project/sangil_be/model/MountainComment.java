package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MountainComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mountainCommentId;

    @Column(nullable = false)
    private Long mountain100Id;

    @Column(nullable = false)
    private String mountainComment;

    @Column(nullable = false)
    private int star;
}

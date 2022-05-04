package com.project.sangil_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CourseComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "courseId")
    @JsonIgnore

    private Course course;
}

package com.project.sangil_be.model;

import com.project.sangil_be.dto.CompleteRequestDto;
import com.project.sangil_be.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Completed extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completeId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long mountainId;

    @Column(nullable = false)
    private Integer send;

    @Column(nullable = false)
    private Double totalDistance;

    @Column(nullable = false)
    private String totalTime;

    public Completed(Long mountainId, Integer send, Long userId) {
        this.mountainId =mountainId;
        this.send=send;
        this.userId=userId;
        this.totalTime = "0";
        this.totalDistance = 0d;
    }

    public void update(CompleteRequestDto completeRequestDto) {
        this.totalDistance=completeRequestDto.getTotalDistance();
        this.totalTime=completeRequestDto.getTotalTime();
    }
}

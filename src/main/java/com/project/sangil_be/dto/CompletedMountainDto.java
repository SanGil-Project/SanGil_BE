package com.project.sangil_be.dto;

import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.Mountain;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CompletedMountainDto {
    private Long completedId;
    private String mountain;
    private String totalDistance;
    private String totalTime;
    private LocalDate createDate;

    public CompletedMountainDto(Completed completed, Mountain mountain) {
        this.completedId = completed.getCompleteId();
        this.mountain = mountain.getMountain();
        this.totalDistance = String.format("%.1f", completed.getTotalDistance());
        this.totalTime = completed.getTotalTime();
        this.createDate = completed.getCreateDate();

    }
}

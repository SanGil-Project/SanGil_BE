package com.project.sangil_be.tracking.dto;

import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.Mountain;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TrackingListDto {

    private Long completedId;
    private String mountain;
    private Double totalDistance;
    private String totalTime;
    private LocalDateTime createdAt;
    private List<TrackingResponseDto> trackingList;

    public TrackingListDto(Long completedId, Mountain mountain, Completed completed, List<TrackingResponseDto> trackingResponseDtoList) {
        this.completedId = completedId;
        this.mountain = mountain.getMountain();
        this.totalDistance=completed.getTotalDistance();
        this.totalTime=completed.getTotalTime();
        this.createdAt = completed.getCreatedAt();
        this.trackingList = trackingResponseDtoList;
    }
}

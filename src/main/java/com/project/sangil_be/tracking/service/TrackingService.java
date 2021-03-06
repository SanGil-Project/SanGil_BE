package com.project.sangil_be.tracking.service;

import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.Mountain;
import com.project.sangil_be.model.Tracking;
import com.project.sangil_be.mountain.repository.MountainCommentRepository;
import com.project.sangil_be.mountain.repository.MountainRepository;
import com.project.sangil_be.mypage.dto.CompleteRequestDto;
import com.project.sangil_be.mypage.dto.TitleDto;
import com.project.sangil_be.mypage.repository.CompletedRepository;
import com.project.sangil_be.party.dto.TitleResponseDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.etc.utils.TitleUtil;
import com.project.sangil_be.tracking.dto.*;
import com.project.sangil_be.tracking.repository.TrackingRepository;
import com.project.sangil_be.etc.utils.DistanceToUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrackingService {
    private final TrackingRepository trackingRepository;
    private final CompletedRepository completedRepository;
    private final MountainRepository mountainRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final TitleUtil titleService;

    // 트래킹 시작
    @Transactional
    public StartTrackingResponseDto startMyLocation(Long mountainId, StartTrackingRequestDto startTrackingRequestDto, UserDetailsImpl userDetails) {
        Completed completed = new Completed(mountainId, startTrackingRequestDto.getSend(), userDetails.getUser().getUserId());
        Mountain mountain = mountainRepository.findByMountainId(mountainId);
        completedRepository.save(completed);
        return new StartTrackingResponseDto(completed.getCompleteId(), mountain.getMountainImgUrl());
    }

    // 맵 트래킹 5초 마다 저장
//    @Transactional
    public DistanceResponseDto saveMyLocation(Long completedId, TrackingRequestDto trackingRequestDto, UserDetailsImpl userDetails) {
        List<Tracking> trackinglist = trackingRepository.findAllByCompletedId(completedId);
        Completed completed = completedRepository.findByCompleteId(completedId);
        Mountain mountain = mountainRepository.findByMountainId(completed.getMountainId());
        Tracking saveTracking = new Tracking();
        saveTracking.setMountain(mountain);
        saveTracking.setUser(userDetails.getUser());
        DistanceResponseDto distanceResponseDto = new DistanceResponseDto();
        if (trackinglist.size() == 0) {
            saveTracking.setCompletedId(completedId);
            saveTracking.setLat(trackingRequestDto.getLat());
            saveTracking.setLng(trackingRequestDto.getLng());
            Double distanceM = 0d;
            Double distanceK = 0d;
            saveTracking.setDistanceM(distanceM);
            saveTracking.setDistanceK(distanceK);

            trackingRepository.save(saveTracking);
            distanceResponseDto.setDistanceM(distanceM);
            distanceResponseDto.setDistanceK(Math.round(distanceK*100)/100.0);
        } else {
            for (int i = trackinglist.size() - 1; i < trackinglist.size(); i++) {
                Double distanceM = DistanceToUser.distance(trackinglist.get(i).getLat(), trackinglist.get(i).getLng(), trackingRequestDto.getLat(), trackingRequestDto.getLng(), "meter");
                Double distanceK = DistanceToUser.distance(trackinglist.get(i).getLat(), trackinglist.get(i).getLng(), trackingRequestDto.getLat(), trackingRequestDto.getLng(), "kilometer");
                saveTracking.setCompletedId(completedId);
                saveTracking.setLat(trackingRequestDto.getLat());
                saveTracking.setLng(trackingRequestDto.getLng());
                saveTracking.setDistanceM(distanceM);
                saveTracking.setDistanceK(distanceK);
                trackingRepository.save(saveTracking);
                distanceResponseDto.setDistanceM(distanceM);
                distanceResponseDto.setDistanceK(Math.round(distanceK*100)/100.0);
            }
        }
        return distanceResponseDto;
    }

    // 트래킹 완료 후 저장
    @Transactional
    public TitleResponseDto saveTracking(Long completedId, CompleteRequestDto completeRequestDto, UserDetailsImpl userDetails) {

        List<TitleDto> titleDtoList = titleService.getTrackingTitle(userDetails);

        Completed completed = completedRepository.findByCompleteId(completedId);
        String msg;
        if (mountainCommentRepository.existsByUserIdAndMountainId(completed.getUserId(), completed.getMountainId())) {
            completed.update(completeRequestDto);
            msg = "true";
        } else {
            completed.update(completeRequestDto);
            msg = "false";
        }

        return new TitleResponseDto(titleDtoList, msg);
    }


    // 맵트래킹 삭제 (10분 이하)
    @Transactional
    public void deleteTracking(Long completedId) {
        try {
            completedRepository.deleteByCompleteId(completedId);

        } catch (Exception e) {
            System.out.println("delete 오류");
        }
    }

    // 맵 트래킹 상세페이지
    @Transactional
    public TrackingListDto detailTracking(Long completedId) {
        List<Tracking> trackingList = trackingRepository.findAllByCompletedId(completedId);
        Completed completed = completedRepository.findByCompleteId(completedId);
        List<TrackingResponseDto> trackingResponseDtoList = new ArrayList<>();
        Mountain mountain = mountainRepository.findByMountainId(completed.getMountainId());
        for (Tracking tracking : trackingList) {
            TrackingResponseDto trackingResponseDto = new TrackingResponseDto(tracking.getLat(), tracking.getLng());
            trackingResponseDtoList.add(trackingResponseDto);
        }
        return new TrackingListDto(completedId, mountain, completed, trackingResponseDtoList);
    }
}

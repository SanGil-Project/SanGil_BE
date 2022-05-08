//package com.project.sangil_be.service;
//
//import com.project.sangil_be.dto.TrackingListDto;
//import com.project.sangil_be.dto.TrackingRequestDto;
//import com.project.sangil_be.dto.TrackingResponseDto;
//import com.project.sangil_be.dto.UserResponseDto;
//import com.project.sangil_be.model.Tracking;
//import com.project.sangil_be.model.User;
//import com.project.sangil_be.repository.TrackingRepository;
//import com.project.sangil_be.repository.UserRepository;
//import com.project.sangil_be.securtiy.UserDetailsImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class TrackingService {
//    private final TrackingRepository trackingRepository;
//    private final UserRepository userRepository;
//
//    //맵 트래킹 정보 저장
//    @Transactional
//    public void saveMyLocation(TrackingRequestDto trackingRequestDto, UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//
//        Tracking tracking = new Tracking(trackingRequestDto, user);
//
//        trackingRepository.save(tracking);
//    }
//
//    @Transactional
//    public TrackingListDto getAllLocation(UserDetailsImpl userDetails) {
//        List<Tracking> trackingList = trackingRepository.findAllByUser(userDetails.getUser());
//        Optional<User> user = userRepository.findById(userDetails.getUser().getUserId());
//        UserResponseDto userResponseDto = new UserResponseDto(user);
//        List<TrackingResponseDto> trackingResponseDtoList = new ArrayList<>();
//
//        for (Tracking tracking : trackingList) {
//            TrackingResponseDto trackingResponseDto = new TrackingResponseDto(tracking.getLat(), tracking.getLng(), tracking.getTimer());
//            trackingResponseDtoList.add(trackingResponseDto);
//        }
//
//        return new TrackingListDto(userResponseDto, trackingResponseDtoList);
//    }
//}

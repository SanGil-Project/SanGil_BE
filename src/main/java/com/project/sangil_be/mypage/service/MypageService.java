package com.project.sangil_be.mypage.service;

import com.project.sangil_be.feed.dto.FeedResponseDto;
import com.project.sangil_be.feed.repository.FeedRepository;
import com.project.sangil_be.feed.repository.GoodRepository;
import com.project.sangil_be.login.repository.UserRepository;
import com.project.sangil_be.mountain.dto.BookMarkDto;
import com.project.sangil_be.mountain.dto.BookMarkResponseDto;
import com.project.sangil_be.mountain.repository.BookMarkRepository;
import com.project.sangil_be.mountain.repository.MountainCommentRepository;
import com.project.sangil_be.mountain.repository.MountainRepository;
import com.project.sangil_be.mypage.dto.*;
import com.project.sangil_be.mypage.repository.CompletedRepository;
import com.project.sangil_be.mypage.repository.GetTitleRepository;
import com.project.sangil_be.mypage.repository.UserTitleRepository;
import com.project.sangil_be.etc.utils.TitleUtil;
import com.project.sangil_be.etc.utils.S3.S3Service;
import com.project.sangil_be.login.dto.UserResponseDto;
import com.project.sangil_be.login.dto.UsernameRequestDto;
import com.project.sangil_be.model.User;
import com.project.sangil_be.model.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.etc.utils.Calculator;
import com.project.sangil_be.etc.utils.DistanceToUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final UserTitleRepository userTitleRepository;
    private final GetTitleRepository getTitleRepository;
    private final UserRepository userRepository;
    private final CompletedRepository completedRepository;
    private final MountainRepository mountainRepository;
    private final BookMarkRepository bookMarkRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final S3Service s3Service;
    private final TitleUtil titleService;
    private final FeedRepository feedRepository;
    private final GoodRepository goodRepository;
    private final Calculator calculator;

    // 맵트래킹 마이페이지
    public List<CompletedListDto> myPageTracking(UserDetailsImpl userDetails) {
        List<Completed> completed = completedRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<CompletedListDto> completedListDtos = new ArrayList<>();
        for (Completed complete : completed) {
            if(complete.getTotalDistance() != 0) {
                Mountain mountain = mountainRepository.findByMountainId(complete.getMountainId());
                CompletedListDto completedListDto = new CompletedListDto(complete, mountain);
                completedListDtos.add(completedListDto);
            }
        }
        return completedListDtos;
    }

    // 맵트래킹 마이페이지 산 선택
    public List<CompletedMountainDto> selectMountain(Long mountainId, UserDetailsImpl userDetails) {
        List<Completed> completedList = completedRepository.findAllByMountainIdAndUserId(mountainId, userDetails.getUser().getUserId());
        Mountain mountain = mountainRepository.findByMountainId(mountainId);
        List<CompletedMountainDto> completedMountainDtos = new ArrayList<>();
        for (Completed completed : completedList) {
            if(completed.getTotalDistance() != 0) {
                CompletedMountainDto completedMountainDto = new CompletedMountainDto(completed, mountain);
                completedMountainDtos.add(completedMountainDto);
            }
        }
        return completedMountainDtos;
    }

    // 닉네임 중복체크
    public String usernameCheck(UsernameRequestDto usernameRequestDto) {
        Optional<User> user = userRepository.findByNickname(usernameRequestDto.getNickname());
        if(user.isPresent()) {
            return "false";
        }else {
            return "true";
        }
    }

    // nickname 수정
    @Transactional
    public UserResponseDto editname(UsernameRequestDto usernameRequestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUserId(userDetails.getUser().getUserId());
        user.editname(usernameRequestDto);
        return new UserResponseDto(user);
    }

    //userimageUrl 수정
    @Transactional
    public void editimage(MultipartFile multipartFile, User user) {

        String[] key = user.getUserImgUrl().split(".com/");
        String imageKey = key[key.length - 1];
        String profileImageUrl = s3Service.reupload(multipartFile, "profileimage", imageKey);

        user.editimage(profileImageUrl);
        userRepository.save(user);
    }

    // 쿼리 페이지처리
    // 유저가 즐겨찾기한 산 가져오는 즐겨찾기
    public BookMarkDto getBookMarkMountain(double lat, double lng, UserDetailsImpl userDetails, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, 6);
        Page<BookMarkResponseDto> bookMarkResponseDtos = bookMarkRepository.bookMarkMountain(userDetails.getUser().getUserId(), pageRequest);
        for (BookMarkResponseDto bookMarkResponseDto : bookMarkResponseDtos) {
            boolean bookMarkChk = bookMarkRepository.existsByMountainIdAndUserId(bookMarkResponseDto.getMountainId(), userDetails.getUser().getUserId());
            Mountain mountain = mountainRepository.findByMountainId(bookMarkResponseDto.getMountainId());
            Double distance = DistanceToUser.distance(lat, lng, mountain.getLat(), mountain.getLng(), "kilometer");
            bookMarkResponseDto.setBookMarkChk(bookMarkChk);
            bookMarkResponseDto.setDistance(Math.round(distance * 100) / 100.0);
        }
        return new BookMarkDto(bookMarkResponseDtos);
    }

//        List<BookMark> bookMarkList = bookMarkRepository.findAllByUserId(userDetails.getUser().getUserId());
//        List<BookMarkResponseDto> bookMarkResponseDtos = new ArrayList<>();
//
//        for (BookMark bookMark : bookMarkList) {
//            boolean bookMarkChk = bookMarkRepository.existsByMountainIdAndUserId(bookMark.getMountainId(),
//                    userDetails.getUser().getUserId());
//            Mountain mountain = mountainRepository.findById(bookMark.getMountainId()).orElseThrow(
//                    () -> new IllegalArgumentException("해당하는 산이 없습니다.")
//            );
//
//            //유저와 즐겨찾기한 산과의 거리 계산
//            Double distance = DistanceToUser.distance(lat, lng, mountain.getLat(),
//                    mountain.getLng(), "kilometer");
//
//            int star = 0;
//            Double starAvr = 0d;
//            for (int i = 0; i < 10; i++) {
//                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(bookMark.getMountainId());
//                if (mountainComments.size() == 0) {
//                    starAvr = 0d;
//                } else {
//                    star += mountainComments.get(i).getStar();
//                    starAvr = (double) star / mountainComments.size();
//                }
//            }
//            bookMarkResponseDtos.add(new BookMarkResponseDto(mountain, bookMarkChk, starAvr, distance));
//        }
//        return bookMarkResponseDtos;
//    }

    // 칭호 리스트
    public UserTitleResponseDto getUserTitle(UserDetailsImpl userDetails) {
        List<TitleDto> titleDtoList = titleService.getAllTitle(userDetails.getUser());
        List<UserTitle> userTitles = userTitleRepository.findAll();
        List<GetTitle> getTitles = getTitleRepository.findAllByUser(userDetails.getUser());

        HashMap<String, Boolean> title = new HashMap<>();
        for (int i = 0; i < userTitles.size(); i++) {
            title.put(userTitles.get(i).getUserTitle(), false);
            for (int j = 0; j < getTitles.size(); j++) {
                if (userTitles.get(i).getUserTitle().equals(getTitles.get(j).getUserTitle())) {
                    title.replace(userTitles.get(i).getUserTitle(), false, true);
                }
            }
        }

        List<UserTitleDto> userTitleDtos = new ArrayList<>();
        for (String s : title.keySet()) {
            UserTitle userTitle = userTitleRepository.findByUserTitle(s);
            if (title.get(s) == true) {
                if (s.equals(userDetails.getUser().getUserTitle())) {
                    UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getCTitleImgUrl(), title.get(userTitle.getUserTitle()));
                    userTitleDtos.add(userTitleDto);
                } else {
                    UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getBTitleImgUrl(), title.get(userTitle.getUserTitle()));
                    userTitleDtos.add(userTitleDto);
                }
            } else {
                UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getQTitleImgUrl(), title.get(userTitle.getUserTitle()));
                userTitleDtos.add(userTitleDto);
            }
        }

        return new UserTitleResponseDto(userTitleDtos, titleDtoList);
    }

    // 칭호 변경
    @Transactional
    public ChangeTitleDto putUserTitle(ChangeTitleRequestDto requestDto, UserDetailsImpl userDetails) {
        UserTitle userTitle2 = userTitleRepository.findByUserTitle(userDetails.getUser().getUserTitle());
        User user = userRepository.findByUserId(userDetails.getUser().getUserId());
        UserTitle userTitle = userTitleRepository.findByUserTitle(requestDto.getUserTitle());
        user.update(userTitle);
        return new ChangeTitleDto(userDetails, userTitle, userTitle2);
    }

    // 쿼리문 수정 필요
    // 마이페이지 피드 10개
    public List<FeedResponseDto> myFeeds(UserDetailsImpl userDetails) {
        List<Feed> feedList = feedRepository.findAllByUserOrderByCreatedAtDesc(userDetails.getUser());
        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
        if (feedList.size() < 10) {
          for (int i = 0; i < feedList.size(); i++) {
              int goodCnt = goodRepository.findByFeedId(feedList.get(i).getFeedId()).size();
              boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feedList.get(i).getFeedId(), userDetails.getUser().getUserId());
              long beforeTime = ChronoUnit.MINUTES.between(feedList.get(i).getCreatedAt(), LocalDateTime.now());
              FeedResponseDto feedResponseDto = new FeedResponseDto(feedList.get(i), goodCnt, goodStatus,calculator.time(beforeTime));
              feedResponseDtos.add(feedResponseDto);
          }
            return feedResponseDtos;
        } else {
            for (int i = 0; i < 10; i++) {
                int goodCnt = goodRepository.findByFeedId(feedList.get(i).getFeedId()).size();
                boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feedList.get(i).getFeedId(), userDetails.getUser().getUserId());
                long beforeTime = ChronoUnit.MINUTES.between(feedList.get(i).getCreatedAt(), LocalDateTime.now());
                FeedResponseDto feedResponseDto = new FeedResponseDto(feedList.get(i), goodCnt, goodStatus,calculator.time(beforeTime));
                feedResponseDtos.add(feedResponseDto);
            }
            return feedResponseDtos;

        }
    }

}


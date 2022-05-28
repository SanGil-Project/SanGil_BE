package com.project.sangil_be.etc.utils;

import com.project.sangil_be.mypage.dto.TitleDto;
import com.project.sangil_be.feed.repository.FeedRepository;
import com.project.sangil_be.feed.repository.GoodRepository;
import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.GetTitle;
import com.project.sangil_be.model.Mountain;
import com.project.sangil_be.model.User;
import com.project.sangil_be.mountain.repository.MountainCommentRepository;
import com.project.sangil_be.mountain.repository.MountainRepository;
import com.project.sangil_be.mypage.repository.CompletedRepository;
import com.project.sangil_be.mypage.repository.GetTitleRepository;
import com.project.sangil_be.party.repository.AttendRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TitleUtil {
    private final FeedRepository feedRepository;
    private final GetTitleRepository getTitleRepository;
    private final GoodRepository goodRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final AttendRepository attendRepository;
    private final MountainRepository mountainRepository;
    private final CompletedRepository completedRepository;

    public List<TitleDto> getFeedTitle(User user) {
        List<TitleDto> titleDtoList = new ArrayList<>();
        String userTitle;
        String userTitleImgUrl;
        Long cnt = feedRepository.countAllByUser(user);

        if (getTitleRepository.findByUserAndUserTitle(user, "예비 찰칵러").isPresent()) {

            System.out.println("예비 찰칵러 보유중");
        } else if (cnt == 1) {
            userTitle = "예비 찰칵러";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/dQ1lIudFdm.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, user);
            getTitleRepository.save(getTitle);
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        }
        return titleDtoList;
    }

    public List<TitleDto> getGoodTitle(UserDetailsImpl userDetails) {
        List<TitleDto> titleDtoList = new ArrayList<>();
        String userTitle;
        String userTitleImgUrl;
        Long cnt = goodRepository.countAllByUserId(userDetails.getUser().getUserId());
        if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "이구역의연습생").isPresent()) {
            System.out.println("이구역의연습생 보유중");
        } else if (cnt >= 1) {
            userTitle = "이구역의연습생";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/ZFoUkXDMAD.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            getTitleRepository.save(getTitle);
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        }
        if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "이구역의연예인").isPresent()) {
            System.out.println("이구역의연예인 보유중");
        } else if (cnt >= 100) {
            userTitle = "이구역의연예인";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/woHzXbI6mo.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            getTitleRepository.save(getTitle);
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        }
        return titleDtoList;
    }

    public List<TitleDto> getCommentTitle(UserDetailsImpl userDetails) {
        List<TitleDto> titleDtoList = new ArrayList<>();
        String userTitle;
        String userTitleImgUrl;
        Long cnt = mountainCommentRepository.countAllByUserId(userDetails.getUser().getUserId());
        if (cnt == 10) {
            userTitle = "셰르파";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/rSuxu63C1v.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "셰르파").isPresent()) {
                System.out.println("셰르파 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        }
        return titleDtoList;
    }

    public List<TitleDto> getAllTitle(User user) {
        List<TitleDto> titleDtoList = new ArrayList<>();
        String userTitle;
        String userTitleImgUrl;
        int cnt = getTitleRepository.countAllByUser(user);
        if (getTitleRepository.findByUserAndUserTitle(user, "내가~~!! 등!!신!!!").isPresent()) {
            System.out.println("내가~~!! 등!!신!!! 보유중");
        } else if (cnt == 19) {
            userTitle = "내가~~!! 등!!신!!!";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/Xgj8xW63TZ.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, user);
            getTitleRepository.save(getTitle);
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        }
        return titleDtoList;
    }

    public List<TitleDto> getAttendTitle(UserDetailsImpl userDetails) {
        List<TitleDto> titleDtoList = new ArrayList<>();
        String userTitle;
        String userTitleImgUrl;
        Long cnt = attendRepository.countAllByUserId(userDetails.getUser().getUserId());
        if (cnt == 1) {
            userTitle = "아싸중에인싸";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/CAnkO4OgfK.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "아싸중에인싸").isPresent()) {
                System.out.println("아싸중에인싸 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        } else if (cnt == 10) {
            userTitle = "인싸....?";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/KNUrQ0mhZ4.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "인싸....?").isPresent()) {
                System.out.println("인싸....? 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        } else if (cnt == 50) {
            userTitle = "인싸중에인싸";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/KNUrQ0mhZ4.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "인싸중에인싸").isPresent()) {
                System.out.println("인싸중에인싸 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        } else if (cnt == 100) {
            userTitle = "산길인맥왕";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/CAnkO4OgfK.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "산길인맥왕").isPresent()) {
                System.out.println("산길인맥왕 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        }
        return titleDtoList;
    }

    public List<TitleDto> getTrackingTitle(UserDetailsImpl userDetails) {
        List<Completed> completedList = completedRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<TitleDto> titleDtoList = new ArrayList<>();
        double distance = 0d;
        double height = 0d;
        for (Completed completed : completedList) {
            Mountain mountain = mountainRepository.findByMountainId(completed.getMountainId());
            distance += completed.getTotalDistance();
            height += mountain.getHeight();
        }
        String userTitle;
        String userTitleImgUrl;
        int cnt = completedList.size();
        if (cnt == 1) {
            userTitle = "방구석 홍길";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/hwxojSZLBS.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "방구석 홍길").isPresent()) {
                System.out.println("방구석 홍길 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        } else if (cnt == 3) {
            userTitle = "리틀홍길";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/7DX5Hpjndy.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "리틀홍길").isPresent()) {
                System.out.println("리틀홍길 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        } else if (cnt == 10) {
            userTitle = "내장래희망 홍길형님";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/ubNAtaaBLG.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "내장래희망 홍길형님").isPresent()) {
                System.out.println("내장래희망 홍길형님 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        } else if (cnt == 100) {
            userTitle = "UM.....홍길?";
            userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/97cmvqWD1f.png";
            GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "UM.....홍길").isPresent()) {
                System.out.println("UM.....홍길 보유중");
            }else {
                getTitleRepository.save(getTitle);
            }
            titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
        }

        if (distance >= 1 && distance < 100) {
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "아직 여기라고?").isPresent()) {
                System.out.println("아직 여기라고? 보유중");
            } else {
                userTitle = "아직 여기라고?";
                userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/1rgDaH9rqW.png";
                GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
                getTitleRepository.save(getTitle);
                titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
            }
        } else if (distance >= 100 && distance < 1000) {

            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "백만불짜리다리").isPresent()) {
                System.out.println("백만불짜리다리 보유중");
            } else {
                userTitle = "백만불짜리다리?";
                userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/5V6CWBTeni.png";
                GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
                getTitleRepository.save(getTitle);
                titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
            }
        } else if (distance > 1000) {
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "산타고 전국일주").isPresent()) {
                System.out.println("산타고 전국일주 보유중");
            } else {
                userTitle = "산타고 전국일주";
                userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/TuRSygz4S6.png";
                GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
                getTitleRepository.save(getTitle);
                titleDtoList.add(new TitleDto(userTitle, userTitleImgUrl));
            }
        }

        if (height >= 10 && height < 30) {
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "내가 탄 산 높이 히말라야").isPresent()) {
                System.out.println("내가 탄 산 높이 히말라야 보유중");
            } else {
                userTitle = "내가 탄 산 높이 히말라야";
                userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/ayb4NtqfgO.png";
                GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
                getTitleRepository.save(getTitle);
                TitleDto titleDto = new TitleDto(userTitle, userTitleImgUrl);
                titleDtoList.add(titleDto);
            }
        } else if (height >= 30 && height < 1000) {

            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "구름위를걷는자").isPresent()) {
                System.out.println("구름위를걷는자 보유중");
            } else {
                userTitle = "구름위를걷는자";
                userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/zd3QVGvdn3.png";
                GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
                getTitleRepository.save(getTitle);
                TitleDto titleDto = new TitleDto(userTitle, userTitleImgUrl);
                titleDtoList.add(titleDto);
            }
        } else if (height > 1000) {
            if (getTitleRepository.findByUserAndUserTitle(userDetails.getUser(), "대기권 돌파~").isPresent()) {
                System.out.println("대기권 돌파~ 보유중");
            } else {
                userTitle = "대기권 돌파~";
                userTitleImgUrl = "https://i.esdrop.com/d/f/JdarL6WQ6C/H4ri7zAS3b.png";
                GetTitle getTitle = new GetTitle(userTitle, userTitleImgUrl, userDetails.getUser());
                getTitleRepository.save(getTitle);
                TitleDto titleDto = new TitleDto(userTitle, userTitleImgUrl);
                titleDtoList.add(titleDto);
            }
        }
        return titleDtoList;
    }
}

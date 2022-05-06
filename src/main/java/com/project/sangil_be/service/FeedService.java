package com.project.sangil_be.service;

import com.project.sangil_be.S3.S3Service;
import com.project.sangil_be.dto.FeedResponseDto;
import com.project.sangil_be.dto.GoodCheckResponseDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.Good;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.FeedRepository;
import com.project.sangil_be.repository.GoodRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FeedService {
    private final S3Service s3Service;
    private final FeedRepository feedRepository;
    private final GoodRepository goodRepository;

    public FeedResponseDto saveFeed(String feedContent, MultipartFile multipartFile, UserDetailsImpl userDetails){

        User user = userDetails.getUser();
        String feedImageUrl = s3Service.upload(multipartFile, "feedimage");

        Feed feed = new Feed(feedContent, feedImageUrl, user);
        feedRepository.save(feed);

        LocalDateTime createdAt = feed.getCreatedAt();


        FeedResponseDto feedResponseDto = new FeedResponseDto(user,feed, 0L );
        return feedResponseDto;

    }


    public GoodCheckResponseDto goodCheck(Long feedId, User user) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new IllegalArgumentException("피드가 없습니다.")
        );

        Optional<Good> good = goodRepository.findByFeedIdAndUserId(feedId, user.getUserId());

        if (!good.isPresent()) {
            Good savegood = new Good(feed, user);

            goodRepository.save(savegood);

        } else {
            goodRepository.deleteById(good.get().getGoodId());


        }
        boolean result = goodRepository.existsByFeedIdAndUserId(feed.getFeedId(), user.getUserId());

        GoodCheckResponseDto goodCheckResponseDto = new GoodCheckResponseDto(result);

        System.out.println(goodCheckResponseDto);

        return goodCheckResponseDto;
    }
    
    @Transactional
    public void deletefeed(Long feedId, Optional<Feed> feed) {

        feedRepository.deleteById(feedId);
        goodRepository.deleteByFeedId(feedId);

        String [] key =feed.get().getFeedImgUrl().split(".com/");
        String imageKey = key[key.length-1];
        System.out.println(imageKey);

        s3Service.deletefeed(imageKey);

    }
}

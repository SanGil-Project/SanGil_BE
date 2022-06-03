package com.project.sangil_be.feed.service;

import com.project.sangil_be.etc.utils.Validator;
import com.project.sangil_be.feed.dto.*;
import com.project.sangil_be.mypage.dto.TitleDto;
import com.project.sangil_be.etc.utils.TitleUtil;
import com.project.sangil_be.etc.utils.S3.S3Service;
import com.project.sangil_be.model.User;
import com.project.sangil_be.model.*;
import com.project.sangil_be.feed.repository.FeedCommentRepository;
import com.project.sangil_be.feed.repository.FeedRepository;
import com.project.sangil_be.feed.repository.GoodRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.etc.utils.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final S3Service s3Service;
    private final FeedRepository feedRepository;
    private final GoodRepository goodRepository;
    private final TitleUtil titleService;
    private final FeedCommentRepository feedCommentRepository;
    private final Calculator calculator;
    private final Validator validator;

    // 피드 작성
    public FeedResponseDto saveFeed(String feedContent, MultipartFile multipartFile, UserDetailsImpl userDetails) {
        String feedImgUrl = s3Service.upload(multipartFile, "feedimage");
        Feed feed = new Feed(feedContent, feedImgUrl, userDetails.getUser());
        feedRepository.save(feed);

        boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feed.getFeedId(), userDetails.getUser().getUserId());
        long beforeTime = ChronoUnit.MINUTES.between(feed.getCreatedAt(),LocalDateTime.now());
        return new FeedResponseDto(userDetails.getUser(), feed, 0,calculator.time(beforeTime));

    }

    // 피드 좋아요
    public GoodCheckResponseDto goodCheck(Long feedId, UserDetailsImpl userDetails) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new IllegalArgumentException("피드가 없습니다.")
        );
        try {
            Optional<Good> good = goodRepository.findByFeedIdAndUserId(feedId, userDetails.getUser().getUserId());
            if (!good.isPresent()) {
                Good savegood = new Good(feed, userDetails.getUser());
                goodRepository.save(savegood);

            } else {
                goodRepository.deleteById(good.get().getGoodId());
            }
        } catch (Exception e) {
            System.out.println("좋아요 예외처리");
        };

        boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feedId, userDetails.getUser().getUserId());
        int goodCnt = goodRepository.countAllByFeedId(feedId);

        return new GoodCheckResponseDto(goodStatus,goodCnt);
    }

    // 피드 삭제
    @Transactional
    public void deletefeed(Long feedId, UserDetailsImpl userDetails) {
        Optional<Feed> feed = feedRepository.findById(feedId);
        validator.feedAuthCheck(feedId, userDetails.getUser().getUserId());
        feedRepository.deleteById(feedId);
        goodRepository.deleteByFeedId(feedId);
        String[] key = feed.get().getFeedImgUrl().split(".com/");
        String imageKey = key[key.length - 1];
        s3Service.deletefeed(imageKey);
    }

    // 피드 상세
    public FeedDetailResponseDto feedDetail(Long feedId, UserDetailsImpl userDetails, int pageNum) {
        Feed feed = feedRepository.findByFeedId(feedId);
        long feedBeforeTime = ChronoUnit.MINUTES.between(feed.getCreatedAt(), LocalDateTime.now());
        Integer goodCnt = goodRepository.countAllByFeedId(feedId);
        boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feedId, userDetails.getUser().getUserId());
        PageRequest pageRequest = PageRequest.of(pageNum, 7);
        Page<CommentResponseDto> commentList = feedCommentRepository.getCommentList(feedId, pageRequest);
        for (CommentResponseDto commentResponseDto : commentList) {
            FeedComment feedComment = feedCommentRepository.findByFeedCommentId(commentResponseDto.getCommentId());
            long CommentBeforeTime = ChronoUnit.MINUTES.between(feedComment.getCreatedAt(), LocalDateTime.now());
            commentResponseDto.setBeforeTime(calculator.time(CommentBeforeTime));
        }
        FeedCommentListDto feedCommentListDto = new FeedCommentListDto(commentList);
        return new FeedDetailResponseDto(feed, goodCnt, goodStatus, feedCommentListDto, calculator.time(feedBeforeTime));
    }

    // 쿼리
    // 나의 피드
    public FeedListResponseDto myfeeds(UserDetailsImpl userDetails, int pageNum) {
        List<Feed> feed = feedRepository.findAllByUserOrderByCreatedAtDesc(userDetails.getUser());
        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
        for (Feed feeds : feed) {
            int goodCnt = goodRepository.findByFeedId(feeds.getFeedId()).size();
            boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feeds.getFeedId(), userDetails.getUser().getUserId());
            long beforeTime = ChronoUnit.MINUTES.between(feeds.getCreatedAt(), LocalDateTime.now());
            feedResponseDtos.add(new FeedResponseDto(feeds));
        }
        Pageable pageable = getPageable(pageNum);

        int start = pageNum * 15;
        int end = Math.min((start + 15), feed.size());

        Page<FeedResponseDto> page = new PageImpl<>(feedResponseDtos.subList(start, end), pageable, feedResponseDtos.size());
        return new FeedListResponseDto(page);
    }

    private Pageable getPageable(int pageNum) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNum, 15, sort);
    }

    // 쿼리 수정 필요
    // 피드 리스트
    public FeedListResponseDto mainFeed(UserDetailsImpl userDetails, int pageNum) {
        List<Feed> feeds = feedRepository.findAllByOrderByCreatedAtDesc();
        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
        for (Feed feed : feeds) {
            int goodCnt = goodRepository.findByFeedId(feed.getFeedId()).size();
            boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feed.getFeedId(), userDetails.getUser().getUserId());
            long beforeTime = ChronoUnit.MINUTES.between(feed.getCreatedAt(), LocalDateTime.now());
            List<FeedComment> feedComments = feedCommentRepository.findAllByFeedOrderByCreatedAtDesc(feed);
            List<FeedCommentResDto> feedCommentResDtos = new ArrayList<>();

            if (feedComments.size() < 3) {
                for (int i = 0; i < feedComments.size(); i++) {
                    long commentbeforeTime = ChronoUnit.MINUTES.between(feedComments.get(i).getCreatedAt(),LocalDateTime.now());
                    FeedCommentResDto feedCommentResDto = new FeedCommentResDto(feedComments.get(i),calculator.time(commentbeforeTime));
                    feedCommentResDtos.add(feedCommentResDto);
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    long commentbeforeTime = ChronoUnit.MINUTES.between(feedComments.get(i).getCreatedAt(),LocalDateTime.now());
                    FeedCommentResDto feedCommentResDto = new FeedCommentResDto(feedComments.get(i),calculator.time(commentbeforeTime));
                    feedCommentResDtos.add(feedCommentResDto);
                }
            }
            Long commentCnt = feedCommentRepository.countAllByFeed(feed);

            FeedResponseDto feedResponseDto = new FeedResponseDto(feed,goodCnt,goodStatus,calculator.time(beforeTime),commentCnt,feedCommentResDtos);
            feedResponseDtos.add(feedResponseDto);
        }

        Pageable pageable = getPageable(pageNum);
        int start = pageNum * 15;
        int end = Math.min((start + 15), feeds.size());

        Page<FeedResponseDto> page = new PageImpl<>(feedResponseDtos.subList(start, end), pageable, feedResponseDtos.size());
        List<TitleDto> titleDtoList = titleService.getFeedTitle(userDetails.getUser());
        return new FeedListResponseDto(page,titleDtoList);
    }

}

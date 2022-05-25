package com.project.sangil_be.service;

import com.project.sangil_be.S3.S3Service;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.*;
import com.project.sangil_be.repository.FeedCommentRepository;
import com.project.sangil_be.repository.FeedRepository;
import com.project.sangil_be.repository.GoodRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final S3Service s3Service;
    private final FeedRepository feedRepository;
    private final GoodRepository goodRepository;
    private final TitleService titleService;
    private final FeedCommentRepository feedCommentRepository;

    // 피드 작성
    public FeedResponseDto saveFeed(String feedContent, MultipartFile multipartFile, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String feedImgUrl = s3Service.upload(multipartFile, "feedimage");
        Feed feed = new Feed(feedContent, feedImgUrl, user);
        feedRepository.save(feed);

        boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feed.getFeedId(), user.getUserId());
        List<TitleDto> titleDtoList = titleService.getFeedTitle(user);
        FeedResponseDto feedResponseDto = new FeedResponseDto(user, feed, 0, goodStatus, titleDtoList);
        return feedResponseDto;

    }

    // 피드 좋아요
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

        boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feedId, user.getUserId());
        int goodCnt = goodRepository.countAllByFeedId(feedId);
        GoodCheckResponseDto goodCheckResponseDto = new GoodCheckResponseDto(goodCnt, goodStatus);

        return goodCheckResponseDto;
    }

    // 피드 삭제
    @Transactional
    public void deletefeed(Long feedId, Optional<Feed> feed) {
        feedRepository.deleteById(feedId);
        goodRepository.deleteByFeedId(feedId);

        String[] key = feed.get().getFeedImgUrl().split(".com/");
        String imageKey = key[key.length - 1];
        s3Service.deletefeed(imageKey);

    }

    // 피드 상세
    public FeedDetailResponseDto feedDetail(Long feedId, User user, int pageNum) {
        Feed feed = feedRepository.findByFeedId(feedId);
        Integer goodCnt = goodRepository.countAllByFeedId(feedId);
        boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feedId, user.getUserId());
        PageRequest pageRequest = PageRequest.of(pageNum, 7);
        Page<CommentResponseDto> commentList = feedCommentRepository.getCommentList(feedId, pageRequest);
        FeedCommentListDto feedCommentListDto = new FeedCommentListDto(commentList);
        return new FeedDetailResponseDto(feed, goodCnt, goodStatus, feedCommentListDto);
    }

    // 쿼리
    // 나의 피드
    public FeedListResponseDto myfeeds(User user, int pageNum) {
        List<Feed> feed = feedRepository.findByUser(user);
        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
        for (Feed feeds : feed) {
            int goodCnt = goodRepository.findByFeedId(feeds.getFeedId()).size();
            boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feeds.getFeedId(), user.getUserId());
            feedResponseDtos.add(new FeedResponseDto(feeds, goodCnt, goodStatus));
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

            List<FeedComment> feedComments = feedCommentRepository.findAllByFeedOrderByCreatedAtDesc(feed);
            List<FeedCommentResDto> feedCommentResDtos = new ArrayList<>();
            if (feedComments.size() < 3) {
                for (int i = 0; i < feedComments.size(); i++) {
                    FeedCommentResDto feedCommentResDto = new FeedCommentResDto(feedComments.get(i));
                    feedCommentResDtos.add(feedCommentResDto);
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    FeedCommentResDto feedCommentResDto = new FeedCommentResDto(feedComments.get(i));
                    feedCommentResDtos.add(feedCommentResDto);
                }
            }
            Long commentCnt = feedCommentRepository.countAllByFeed(feed);

            FeedResponseDto feedResponseDto = new FeedResponseDto(feed,goodCnt,goodStatus,commentCnt,feedCommentResDtos);
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

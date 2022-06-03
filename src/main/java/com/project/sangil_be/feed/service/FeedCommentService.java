package com.project.sangil_be.feed.service;

import com.project.sangil_be.etc.utils.Validator;
import com.project.sangil_be.feed.dto.FeedCommentReqDto;
import com.project.sangil_be.feed.dto.FeedCommentResDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.FeedComment;
import com.project.sangil_be.feed.repository.FeedCommentRepository;
import com.project.sangil_be.feed.repository.FeedRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.etc.utils.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;
    private final FeedRepository feedRepository;
    private final Calculator calculator;
    private final Validator validator;

    // createdAt 수정
    // 댓글 작성
    public FeedCommentResDto writeComment(Long feedId, FeedCommentReqDto feedCommentReqDto, UserDetailsImpl userDetails) {
        Feed feed = feedRepository.findByFeedId(feedId);
        FeedComment feedComment = new FeedComment(feedCommentReqDto,feed,userDetails.getUser());
        feedCommentRepository.save(feedComment);
        long commentBeforeTime = ChronoUnit.MINUTES.between(feedComment.getCreatedAt(), LocalDateTime.now());
        return new FeedCommentResDto(feedComment,calculator.time(commentBeforeTime));
    }

    // 댓글 수정
    @Transactional
    public void updateComment(Long feedCommentId, FeedCommentReqDto feedCommentReqDto, UserDetailsImpl userDetails) {
        validator.feedCommentAuthCheck(feedCommentId,userDetails.getUser().getUserId());
        FeedComment feedComment = feedCommentRepository.findByFeedCommentId(feedCommentId);
        feedComment.update(feedCommentReqDto,userDetails);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long feedCommentId, UserDetailsImpl userDetails) {
        validator.feedCommentAuthCheck(feedCommentId,userDetails.getUser().getUserId());
        feedCommentRepository.deleteByFeedCommentId(feedCommentId);
    }


//    // 댓글 상세 페이지
//    public FeedCommentListDto detailComment(Long feedId, UserDetailsImpl userDetails, int pageNum) {
//        Feed feed = feedRepository.findByFeedId(feedId);
//        List<FeedComment> feedComments = feedCommentRepository.findAllByFeedOrderByCreatedAtDesc(feed);
//        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
//        for (FeedComment feedComment : feedComments) {
//            CommentResponseDto commentResponseDto = new CommentResponseDto(feedComment);
//            commentResponseDtos.add(commentResponseDto);
//        }
//        Pageable pageable = getPageable(pageNum);
//        int start = pageNum * 15;
//        int end = Math.min((start + 15), feedComments.size());
//
//        Page<CommentResponseDto> page = new PageImpl<>(commentResponseDtos.subList(start, end), pageable, commentResponseDtos.size());
//        return new FeedCommentListDto(page);
//    }

//    private Pageable getPageable(int pageNum) {
//        Sort.Direction direction = Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, "id");
//        return PageRequest.of(pageNum, 15, sort);
//    }
}

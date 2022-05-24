package com.project.sangil_be.service;

import com.project.sangil_be.dto.FeedCommentReqDto;
import com.project.sangil_be.dto.FeedCommentResDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.FeedComment;
import com.project.sangil_be.repository.FeedCommentRepository;
import com.project.sangil_be.repository.FeedRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;
    private final FeedRepository feedRepository;

    public FeedCommentResDto writeComment(Long feedId, FeedCommentReqDto feedCommentReqDto, UserDetailsImpl userDetails) {
        Feed feed = feedRepository.findByFeedId(feedId);
        FeedComment feedComment = new FeedComment(feedCommentReqDto,feed,userDetails.getUser());
        feedCommentRepository.save(feedComment);
        return new FeedCommentResDto(feedComment);
    }

    @Transactional
    public void updateComment(Long feedCommentId, FeedCommentReqDto feedCommentReqDto, UserDetailsImpl userDetails) {
        FeedComment feedComment = feedCommentRepository.findByFeedCommentId(feedCommentId);
        feedComment.update(feedCommentReqDto,userDetails);
    }

    public void deleteComment(Long feedCommentId) {
        feedCommentRepository.deleteByFeedCommentId(feedCommentId);
    }
}

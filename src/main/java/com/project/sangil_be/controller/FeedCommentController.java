package com.project.sangil_be.controller;

import com.project.sangil_be.dto.FeedCommentReqDto;
import com.project.sangil_be.dto.FeedCommentResDto;
import com.project.sangil_be.dto.MCommentRequestDto;
import com.project.sangil_be.dto.MCommentResponseDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.FeedCommentService;
import com.project.sangil_be.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FeedCommentController {
    private final FeedCommentService feedCommentService;

    // 댓글 작성
    @PostMapping("feeds/comment/{feedId}")
    public FeedCommentResDto writeComment(
            @PathVariable Long feedId,
            @RequestBody FeedCommentReqDto feedCommentReqDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return feedCommentService.writeComment(feedId, feedCommentReqDto, userDetails);
    }

    // 댓글 수정
    @PutMapping("feeds/comment/{feedCommentId}")
    public void updateComment(
            @PathVariable Long feedCommentId,
            @RequestBody FeedCommentReqDto feedCommentReqDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedCommentService.updateComment(feedCommentId, feedCommentReqDto, userDetails);
    }

    // 댓글 삭제
    @DeleteMapping("feeds/comment/{feedCommentId}")
    public void deleteComment(@PathVariable Long feedCommentId){
        feedCommentService.deleteComment(feedCommentId);
    }
}

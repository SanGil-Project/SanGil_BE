package com.project.sangil_be.feed.controller;

import com.project.sangil_be.feed.dto.FeedCommentReqDto;
import com.project.sangil_be.feed.dto.FeedCommentResDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.feed.service.FeedCommentService;
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
    public void deleteComment(@PathVariable Long feedCommentId) {
        feedCommentService.deleteComment(feedCommentId);
    }

//    // 댓글 상세 페이지
//    @GetMapping("/feeds/comment/{feedId}/{pageNum}")
//    public FeedCommentListDto detailComment(@PathVariable Long feedId,
//                                            @PathVariable("pageNum") int pageNum,
//                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return feedCommentService.detailComment(feedId,userDetails,pageNum-1);
//    }

}

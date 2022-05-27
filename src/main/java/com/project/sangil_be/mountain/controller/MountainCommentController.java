package com.project.sangil_be.mountain.controller;

import com.project.sangil_be.mountain.dto.MCommentRequestDto;
import com.project.sangil_be.mountain.dto.MCommentResponseDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.mountain.service.MCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MountainCommentController {
    private final MCommentService mCommentService;

    // 댓글 작성
    @PostMapping("/api/mountain/comment/{mountainId}")
    public MCommentResponseDto writeComment(
            @PathVariable Long mountainId,
            @RequestBody MCommentRequestDto mCommentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mCommentService.writeComment(mountainId, mCommentRequestDto, userDetails);
    }

    // 댓글 수정
    @PutMapping("/api/mountain/comment/{mountainCommentId}")
    public MCommentResponseDto updateComment(
            @PathVariable Long mountainCommentId,
            @RequestBody MCommentRequestDto mCommentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mCommentService.updateComment(mountainCommentId, mCommentRequestDto, userDetails);
    }

    // 댓글 삭제
    @DeleteMapping("/api/mountain/comment/{mountainCommentId}")
    public String deleteComment(@PathVariable Long mountainCommentId){
        return mCommentService.deleteComment(mountainCommentId);
    }

}

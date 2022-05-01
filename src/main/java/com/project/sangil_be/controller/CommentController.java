package com.project.sangil_be.controller;

import com.project.sangil_be.dto.PartyCommentRequestDto;
import com.project.sangil_be.dto.PartyCommentDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //등산 모임 댓글 등록
    @PostMapping("/api/party/comment")
    public PartyCommentDto createPartyComment(@RequestBody PartyCommentRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createPartyComment(requestDto, userDetails);
    }

    //등산 모임 댓글 수정
    @PutMapping("/api/party/comment/{partyCommentId}")
    public PartyCommentDto updatePartyComment(@PathVariable Long partyCommentId,
                                              @RequestBody PartyCommentRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updatePartyComment(partyCommentId, requestDto, userDetails);
    }

    //등산 모임 댓글 삭제
    @DeleteMapping("/api/party/comment/{partyCommentId}")
    public void deletePartyComment(@PathVariable Long partyCommentId) {
        commentService.deletePartyComment(partyCommentId);
    }
}

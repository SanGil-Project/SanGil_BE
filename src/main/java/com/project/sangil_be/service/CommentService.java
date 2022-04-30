package com.project.sangil_be.service;

import com.project.sangil_be.dto.PartyCommentDto;
import com.project.sangil_be.dto.PartyCommentRequestDto;
import com.project.sangil_be.model.Party;
import com.project.sangil_be.model.PartyComment;
import com.project.sangil_be.repository.PartyCommentRepository;
import com.project.sangil_be.repository.PartyRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final PartyCommentRepository partyCommentRepository;
    private final PartyRepository partyRepository;
    private final Validator validator;

    //등산 모임 댓글 생성
    @Transactional
    public PartyCommentDto createPartyComment(PartyCommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Party party = partyRepository.findById(requestDto.getPartyId()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        validator.blankComment(requestDto);

        String partyComm = requestDto.getPartyComment();
        String userTitle = userDetails.getUser().getUserTitle();
        String username = userDetails.getUsername();

        PartyComment partyComment = new PartyComment(party, partyComm, userTitle, username);
        partyCommentRepository.save(partyComment);

        return new PartyCommentDto(partyComment.getPartyCommentId(), partyComment.getPartyComment(), partyComment.getUserTitle(), partyComment.getUsername());
    }

    //등산 모임 댓글 수정
    @Transactional
    public PartyCommentDto updatePartyComment(Long partyCommentId, PartyCommentRequestDto requestDto, UserDetailsImpl userDetails) {
        PartyComment partyComment = partyCommentRepository.findById(partyCommentId).orElseThrow(
                () -> new IllegalArgumentException("찾는 게시글이 없습니다.")
        );
        partyComment.update(requestDto.getPartyComment(), requestDto.getUserTitle(), requestDto.getUsername());
        PartyCommentDto partyCommentDto = new PartyCommentDto(partyComment.getPartyCommentId(), partyComment.getPartyComment(),
                                                              partyComment.getUserTitle(), partyComment.getUsername());
        return partyCommentDto;
    }

    //등산 모임 댓글 삭제
    @Transactional
    public void deletePartyComment(Long partyCommentId) {
        try {
            partyCommentRepository.deleteById(partyCommentId);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("댓글을 삭제하지 못했습니다.");
        }
    }
}

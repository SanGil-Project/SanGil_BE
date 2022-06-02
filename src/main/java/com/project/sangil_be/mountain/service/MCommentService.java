package com.project.sangil_be.mountain.service;

import com.project.sangil_be.etc.utils.Calculator;
import com.project.sangil_be.mountain.dto.MCommentRequestDto;
import com.project.sangil_be.mountain.dto.MCommentResponseDto;
import com.project.sangil_be.mypage.dto.TitleDto;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.mountain.repository.MountainRepository;
import com.project.sangil_be.mountain.repository.MountainCommentRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.etc.utils.TitleUtil;
import com.project.sangil_be.etc.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
//테스트
@RequiredArgsConstructor
@Service
public class MCommentService {
    private final MountainRepository mountainRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final Validator validator;
    private final TitleUtil titleService;
    private final Calculator calculator;

    // 댓글 작성
    public MCommentResponseDto writeComment(Long mountainId, MCommentRequestDto mCommentRequestDto, UserDetailsImpl userDetails) {
        List<MountainComment> comment = mountainCommentRepository.findAllByMountainIdAndUserId(mountainId,userDetails.getUser().getUserId());
        String msg;
        if (comment.size()>=1) {
            msg = "중복";
        } else {
            msg = "작성 가능";
        }
        validator.emptyMComment(mCommentRequestDto); // 아무것도 안쓸때
        MountainComment mountainComment = new MountainComment(mountainId, userDetails, mCommentRequestDto);
        mountainCommentRepository.save(mountainComment);

        List<TitleDto> titleDtoList = titleService.getCommentTitle(userDetails);
        long beforeTime = ChronoUnit.MINUTES.between(mountainComment.getCreatedAt(), LocalDateTime.now());
        return new MCommentResponseDto(mountainComment, userDetails, msg, titleDtoList, calculator.time(beforeTime));
    }

    // 댓글 수정
    @Transactional
    public void updateComment(Long mountainCommentId, MCommentRequestDto mCommentRequestDto, UserDetailsImpl userDetails) {
        validator.mCommentAuthCheck(mountainCommentId,userDetails.getUser().getUserId());
        MountainComment mountainComment = mountainCommentRepository.findById(mountainCommentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        mountainComment.update(mountainComment,mCommentRequestDto,userDetails);
    }

    // 댓글 삭제
    public String deleteComment(Long mountainCommentId, UserDetailsImpl userDetails) {
        validator.mCommentAuthCheck(mountainCommentId,userDetails.getUser().getUserId());
        MountainComment mountainComment = mountainCommentRepository.findById(mountainCommentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        try {
            mountainCommentRepository.deleteById(mountainCommentId);
            return "true";
        } catch (IllegalArgumentException e) {
            return "false";
        }
    }
}

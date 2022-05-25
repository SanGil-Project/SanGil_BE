package com.project.sangil_be.repository;

import com.project.sangil_be.dto.CommentResponseDto;
import com.project.sangil_be.dto.FeedCommentResDto;
import com.project.sangil_be.dto.PartyListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedCommentRepositoryCustom {
    Page<CommentResponseDto> getCommentList(Long feedId, Pageable pageable);
}

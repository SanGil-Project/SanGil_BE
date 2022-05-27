package com.project.sangil_be.feed.repository;

import com.project.sangil_be.feed.dto.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedCommentRepositoryCustom {
    Page<CommentResponseDto> getCommentList(Long feedId, Pageable pageable);
}

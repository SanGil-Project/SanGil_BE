package com.project.sangil_be.repository;

import com.project.sangil_be.dto.CommentResponseDto;
import com.project.sangil_be.dto.FeedCommentResDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.sangil_be.model.QFeedComment.feedComment1;

@RequiredArgsConstructor
public class FeedCommentRepositoryImpl implements FeedCommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommentResponseDto> getCommentList(Long feedId, Pageable pageable) {

        QueryResults<CommentResponseDto> results = queryFactory
                .select(Projections.constructor(CommentResponseDto.class,
                        feedComment1.user.userId,
                        feedComment1.feedCommentId,
                        feedComment1.user.nickname,
                        feedComment1.user.userTitle,
                        feedComment1.user.userImgUrl,
                        feedComment1.feedComment))
                .from(feedComment1)
                .where(feedComment1.feed.feedId.eq(feedId))
                .orderBy(feedComment1.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<CommentResponseDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}

package com.project.sangil_be.repository;

import com.project.sangil_be.dto.BookMarkResponseDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.sangil_be.model.QBookMark.bookMark;
import static com.project.sangil_be.model.QMountain.mountain1;
import static com.project.sangil_be.model.QMountainComment.mountainComment1;
@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BookMarkResponseDto> bookMarkMountain(Long userId, Pageable pageable) {
        QueryResults<BookMarkResponseDto> results = queryFactory
                .select(Projections.constructor(BookMarkResponseDto.class,
                        mountain1.mountainId,
                        mountain1.mountain,
                        mountain1.mountainAddress,
                        mountain1.mountainImgUrl,
                        mountainComment1.star.avg().as("starAvr")))
                .from(bookMark)
                .leftJoin(mountain1).on(mountain1.mountainId.eq(bookMark.mountainId))
                .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
                .where(bookMark.userId.eq(userId))
                .groupBy(mountain1.mountainId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<BookMarkResponseDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}

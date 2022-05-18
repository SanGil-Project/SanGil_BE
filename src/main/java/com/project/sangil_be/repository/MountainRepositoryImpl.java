package com.project.sangil_be.repository;

import com.project.sangil_be.dto.SearchDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.sangil_be.model.QMountain.m;
import static com.project.sangil_be.model.QMountainComment.c;


@RequiredArgsConstructor
public class MountainRepositoryImpl implements MountainRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchDto> searchPage(String keyword, Pageable pageable) {
        QueryResults<SearchDto> results = queryFactory
                .select(Projections.constructor(SearchDto.class,
                        m.mountainId,
                        m.mountain,
                        m.mountainAddress,
                        m.mountainImgUrl,
                        c.star.avg().as("starAvr"),
                        m.lat,
                        m.lng))
                .from(m)
                .leftJoin(c).on(c.mountainId.eq(m.mountainId))
                .where(m.mountain.contains(keyword).or(m.mountainAddress.contains(keyword)))
                .groupBy(m.mountainId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SearchDto> content = results.getResults();

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}

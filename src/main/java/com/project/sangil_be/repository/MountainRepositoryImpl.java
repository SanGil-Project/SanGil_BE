package com.project.sangil_be.repository;

import com.project.sangil_be.dto.NearbyMountainListDto;
import com.project.sangil_be.dto.SearchDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.sangil_be.model.QMountain.mountain1;
import static com.project.sangil_be.model.QMountainComment.mountainComment1;


@RequiredArgsConstructor
public class MountainRepositoryImpl implements MountainRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchDto> searchPage(String keyword, Pageable pageable) {
        QueryResults<SearchDto> results = queryFactory
                .select(Projections.constructor(SearchDto.class,
                        mountain1.mountainId,
                        mountain1.mountain,
                        mountain1.mountainAddress,
                        mountain1.mountainImgUrl,
                        mountainComment1.star.avg().as("starAvr"),
                        mountain1.lat,
                        mountain1.lng))
                .from(mountain1)
                .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
                .where(mountain1.mountain.contains(keyword).or(mountain1.mountainAddress.contains(keyword)))
                .groupBy(mountain1.mountainId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SearchDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<NearbyMountainListDto> nearByMountain(double x1, double x2, double y1, double y2, Pageable pageable) {
        QueryResults<NearbyMountainListDto> results = queryFactory
                .select(Projections.constructor(NearbyMountainListDto.class,
                        mountain1.mountainId,
                        mountain1.mountain,
                        mountain1.mountainImgUrl,
                        mountain1.mountainAddress,
                        mountainComment1.star.avg().as("starAvr")))
                .from(mountain1)
                .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
                .where(mountain1.lat.between(x2, x1).and(mountain1.lng.between(y2, y1)))
                .groupBy(mountain1.mountainId)
                .offset(0)
                .limit(7)
                .fetchResults();

        List<NearbyMountainListDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

}

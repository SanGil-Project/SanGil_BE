package com.project.sangil_be.service;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.QCourse;
import com.project.sangil_be.model.QUser;
import com.project.sangil_be.repository.MountainRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.sangil_be.model.QCourse.*;
import static com.project.sangil_be.model.QMountain.m;
import static com.project.sangil_be.model.QMountainComment.c;
import static com.project.sangil_be.model.QUser.user;

@SpringBootTest
@Transactional
class MountainServiceTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    MountainRepository mountainRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

//    @Test
//    void searchMountain() {
//        String keyword = "악";
//        PageRequest pageable = PageRequest.of(1, 5);
//
//        Page<SearchDto> searchDtos = mountainRepository.searchPage(keyword, pageable);
//        for (SearchDto searchDto : searchDtos) {
//            System.out.println("searchDto = " + searchDto);
//        }
//    }

    @Test
    void searchMountain2() {
        String keyword = "악";
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
                .offset(0)
                .limit(5)
                .fetchResults();

        List<SearchDto> content = results.getResults();
        for (SearchDto searchDto : content) {
            System.out.println("searchDto = " + searchDto);
        }

    }

    @Test
    void searchMountain3() {
        Long mountainId = 1L;
        QueryResults<CommentListDto> results = queryFactory
                .select(Projections.constructor(CommentListDto.class,
                        c.mountainCommentId,
                        c.mountainComment,
                        user.userId,
                        user.nickname,
                        user.userTitle,
                        c.star,
                        c.createdAt))
                .from(c)
                .join(user).on(c.userId.eq(user.userId))
                .where(c.mountainId.eq(mountainId))
                .offset(0)
                .limit(5)
                .fetchResults();

        List<CommentListDto> content = results.getResults();
        for (int i = 0; i < content.size(); i++) {
            System.out.println(content.get(i).getUserId());
        }
    }

//    @Test
//    void searchMountain4() {
//        Long mountainId = 1L;
//        List<MountainResponseDto> fetch = queryFactory
//                .select(Projections.constructor(MountainResponseDto.class,
//                        m.mountainId,
//                        m.mountain,
//                        m.mountainImgUrl,
//                        m.mountainAddress,
//                        m.mountainInfo,
//                        m.height,
//                        c.star.avg().as("starAvr"),
//                        Projections.constructor(CourseListDto.class,
//                                course1.courseId,
//                                course1.course,
//                                course1.courseTime).as("courseDto")))
//                .from(m)
//                .innerJoin(c).on(c.mountainId.eq(c.mountainId))
//                .innerJoin(course1).on(c.mountainId.eq(course1.mountainId))
//                .where(m.mountainId.eq(mountainId))
//                .fetch();
//        for (MountainResponseDto mountainResponseDto : fetch) {
//            System.out.println("mountainResponseDto = " + mountainResponseDto);
//        }
//    }
}
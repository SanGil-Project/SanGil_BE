package com.project.sangil_be.service;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.QBookMark;
import com.project.sangil_be.model.QFeed;
import com.project.sangil_be.model.QParty;
import com.project.sangil_be.repository.MountainRepository;
import com.project.sangil_be.webSocket.ChatMessage;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.project.sangil_be.model.QBookMark.*;
import static com.project.sangil_be.model.QFeed.*;
import static com.project.sangil_be.model.QMountain.mountain1;
import static com.project.sangil_be.model.QMountainComment.mountainComment1;
import static com.project.sangil_be.model.QParty.*;
import static com.project.sangil_be.model.QUser.user;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MountainServiceTest {

    private final MountainRepository mountainRepository;

    @Autowired
    MountainServiceTest(MountainRepository mountainRepository) {
        this.mountainRepository = mountainRepository;
    }

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    void searchMountain() {
        String keyword = "악";
        PageRequest pageable = PageRequest.of(0, 5);

        Page<SearchDto> searchDtos = mountainRepository.searchPage(keyword, pageable);
        List<String> mountains = new ArrayList<>();
        mountains.add("감악산");
        mountains.add("관악산");
        mountains.add("모악산");
        mountains.add("삼악산");
        mountains.add("설악산");

        for (int i = 0; i < searchDtos.getSize(); i++) {
            assertThat(searchDtos.getContent().get(i).getMountain()).isEqualTo(mountains.get(i));
        }
    }

    @Test
    void searchMountain2() {
        String keyword = "악";
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
                .offset(0)
                .limit(5)
                .fetchResults();

        List<SearchDto> content = results.getResults();
        long limit = results.getLimit();
        List<String> mountains = new ArrayList<>();
        mountains.add("감악산");
        mountains.add("관악산");
        mountains.add("모악산");
        mountains.add("삼악산");
        mountains.add("설악산");

        for (int i = 0; i < limit; i++) {
            assertThat(content.get(i).getMountain()).isEqualTo(mountains.get(i));
        }

    }

    @Test
    void searchMountain3() {
        Long mountainId = 1L;
        QueryResults<CommentListDto> results = queryFactory
                .select(Projections.constructor(CommentListDto.class,
                        mountainComment1.mountainCommentId,
                        mountainComment1.mountainComment,
                        user.userId,
                        user.nickname,
                        user.userTitle,
                        mountainComment1.star,
                        mountainComment1.createdAt))
                .from(mountainComment1)
                .join(user).on(mountainComment1.userId.eq(user.userId))
                .where(mountainComment1.mountainId.eq(mountainId))
                .offset(0)
                .limit(5)
                .fetchResults();

        List<CommentListDto> content = results.getResults();
        for (CommentListDto commentListDto : content) {
            System.out.println("commentListDto = " + commentListDto);
        }
    }

    @Test
    void searchParty() throws ParseException {
        String keyword = "모악";
        QueryResults<PartyListDto> results = queryFactory
                .select(Projections.constructor(PartyListDto.class,
                        party.partyId,
                        party.user.nickname,
                        party.user.userTitle,
                        party.mountain,
                        party.address,
                        party.partyDate,
                        party.partyTime,
                        party.maxPeople,
                        party.curPeople,
                        party.completed,
                        party.createdAt))
                .from(party)
                .where(party.mountain.contains(keyword)
                        .or(party.address.contains(keyword)
                                .or(party.title.contains(keyword))))
                .offset(0)
                .limit(6)
                .fetchResults();
        List<PartyListDto> content = results.getResults();
        LocalDate date = LocalDate.now();
        for (PartyListDto partyListDto : content) {
            LocalDate date1 = LocalDate.parse(partyListDto.getPartyDate());
            if (date1.isBefore(date)) {
                partyListDto.setPartyDate("마감되었습니다.");
            }
            System.out.println("partyListDto = " + partyListDto.getPartyDate());
        }
    }

    @Test
    void myPageBookmark() {
        Long userId = 1L;
        QueryResults<BookMarkResponseDto> results = queryFactory
                .select(Projections.constructor(BookMarkResponseDto.class,
                        mountain1.mountainId,
                        mountain1.mountain,
                        mountain1.mountainAddress,
                        mountain1.mountainImgUrl,
                        mountainComment1.star.avg().as("starAvr")))
                .from(mountain1)
                .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
                .leftJoin(bookMark).on(bookMark.mountainId.eq(mountainComment1.mountainId))
                .where(bookMark.userId.eq(userId))
                .groupBy(mountain1.mountainId)
                .offset(0)
                .limit(5)
                .fetchResults();
        List<BookMarkResponseDto> content = results.getResults();
        for (BookMarkResponseDto bookMarkResponseDto : content) {
            System.out.println(bookMarkResponseDto.getMountainAddress());
        }
        long total = results.getTotal();
    }
}
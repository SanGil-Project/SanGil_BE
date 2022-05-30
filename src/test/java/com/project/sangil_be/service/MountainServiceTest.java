package com.project.sangil_be.service;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.*;
import com.project.sangil_be.repository.*;
import com.project.sangil_be.utils.Direction;
import com.project.sangil_be.utils.GeometryUtil;
import com.project.sangil_be.utils.Location;
import com.project.sangil_be.utils.TitleUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

import static com.project.sangil_be.model.QBookMark.*;
import static com.project.sangil_be.model.QFeed.feed;
import static com.project.sangil_be.model.QFeedComment.*;
import static com.project.sangil_be.model.QGood.good;
import static com.project.sangil_be.model.QMountain.mountain1;
import static com.project.sangil_be.model.QMountainComment.mountainComment1;
import static com.project.sangil_be.model.QParty.*;
import static com.project.sangil_be.model.QUser.user;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MountainServiceTest {

    private final MountainRepository mountainRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final BookMarkRepository bookMarkRepository;
    private final TitleUtil titleService;
    private final GetTitleRepository getTitleRepository;
    private final UserTitleRepository userTitleRepository;
    private final UserRepository userRepository;

    @Autowired
    MountainServiceTest(MountainRepository mountainRepository, MountainCommentRepository mountainCommentRepository, BookMarkRepository bookMarkRepository, TitleUtil titleService, GetTitleRepository getTitleRepository, UserTitleRepository userTitleRepository, UserRepository userRepository) {
        this.mountainRepository = mountainRepository;
        this.mountainCommentRepository = mountainCommentRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.titleService = titleService;
        this.getTitleRepository = getTitleRepository;
        this.userTitleRepository = userTitleRepository;
        this.userRepository = userRepository;
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
        String keyword = "악";
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
                .from(bookMark)
                .leftJoin(mountain1).on(mountain1.mountainId.eq(bookMark.mountainId))
                .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
                .where(bookMark.userId.eq(userId))
                .groupBy(mountain1.mountainId)
                .offset(0)
                .limit(5)
                .fetchResults();
        List<BookMarkResponseDto> content = results.getResults();
        for (BookMarkResponseDto bookMarkResponseDto : content) {
            System.out.println(bookMarkResponseDto.getMountainName());
        }
        long total = results.getTotal();
        System.out.println(total);
    }

    @Test
    void nearByMountain() {
        double lat = 36.7683406;
        double lng = 126.4457861;
        double distance = 7; // km 단위 // 대략 반경 5km 이내의 주변 산

        Location northEast = GeometryUtil.calculate(lat, lng, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(lat, lng, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLat();
        double y1 = northEast.getLng();
        double x2 = southWest.getLat();
        double y2 = southWest.getLng();

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
        for (NearbyMountainListDto nearbyMountainListDto : content) {
            System.out.println("nearbyMountainListDto.getMountainName() = " + nearbyMountainListDto.getMountainName());
        }
    }

//    @Test
//    void mainfeed() {
//        Long userId = 1L;
//        QueryResults<FeedResponseDto> results = queryFactory
//                .select(Projections.constructor(FeedResponseDto.class,
//                        feed.user.userId,
//                        feed.feedId,
//                        feed.user.nickname,
//                        feed.user.userTitle,
//                        feed.user.userImgUrl,
//                        feed.feedImgUrl,
//                        feed.feedContent,
//                        feed.createdAt,
//                        good.count(),
//                        JPAExpressions.selectOne().from(good).where(good.userId.eq(userId)).fetchAll().exists()).as("goodStatus"))
//                .from(feed)
//                .leftJoin(good).on(feed.feedId.eq(good.feedId))
//                .where(good.feedId.eq(feed.feedId))
//                .orderBy(feed.createdAt.desc())
//                .limit(7)
//                .fetchResults();
//
//        List<FeedResponseDto> content = results.getResults();
//        for (FeedResponseDto feedResponseDto : content) {
//            System.out.println("feedResponseDto.getGoodCnt() = " + feedResponseDto.getGoodCnt());
//        }
//
//    }

    @Test
    void title() {
        User user = userRepository.findByUserId(1L);
        List<TitleDto> titleDtoList = titleService.getAllTitle(user);
        List<UserTitle> userTitles = userTitleRepository.findAll();
        List<GetTitle> getTitles = getTitleRepository.findAllByUser(user);

        HashMap<String, Boolean> title = new HashMap<>();


        for (int i = 0; i < userTitles.size(); i++) {
            title.put(userTitles.get(i).getUserTitle(), false);
            for (int j = 0; j < getTitles.size(); j++) {
                if (userTitles.get(i).getUserTitle().equals(getTitles.get(j).getUserTitle())) {
                    title.replace(userTitles.get(i).getUserTitle(), false, true);
                }
            }
        }
        List<UserTitleDto> userTitleDtos = new ArrayList<>();
        for (String s : title.keySet()) {
            UserTitle userTitle = userTitleRepository.findByUserTitle(s);
            if (title.get(s) == true) {
                if (s.equals(user.getUserTitle())) {
                    UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getCTitleImgUrl(), title.get(userTitle.getUserTitle()));
                    userTitleDtos.add(userTitleDto);
                } else {
                    UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getBTitleImgUrl(), title.get(userTitle.getUserTitle()));
                    userTitleDtos.add(userTitleDto);
                }
            } else {
                UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getQTitleImgUrl(), title.get(userTitle.getUserTitle()));
                userTitleDtos.add(userTitleDto);
            }
        }
        System.out.println(userTitleDtos.size());


//        UserTitleResponseDto userTitleResponseDto = new UserTitleResponseDto(userTitleDtos, titleDtoList);

    }
    @Test
    public void commentList() {
        Long feedId = 1L;
        QueryResults<FeedCommentResDto> results = queryFactory
                .select(Projections.constructor(FeedCommentResDto.class,
                        feedComment1.feedCommentId,
                        feedComment1.user.userId,
                        feedComment1.user.nickname,
                        feedComment1.user.userTitle,
                        feedComment1.user.userImgUrl,
                        feedComment1.feedComment))
                .from(feedComment1)
                .where(feedComment1.feed.feedId.eq(feedId))
                .offset(0)
                .limit(7)
                .fetchResults();
        List<FeedCommentResDto> content = results.getResults();
        for (FeedCommentResDto feedCommentResDto : content) {
            System.out.println("feedCommentResDto.getFeedComment() = " + feedCommentResDto.getFeedComment());
        }
        long total = results.getTotal();
    }

    @Test
    @DisplayName("10개 랜점으로 보여주기 jpa")
    void searchBefore() {
        List<Mountain> mountainList = mountainRepository.findAll();

        List<Mountain100Dto> mountain100DtoList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Mountain100Dto mountain100Dto = new Mountain100Dto(mountainList.get(i));
            mountain100DtoList.add(mountain100Dto);
        }
        Collections.shuffle(mountain100DtoList);

        List<SearchDto> searchDto = new ArrayList<>();

        int star = 0;
        double starAvr;
        for (int i = 0; i < 10; i++) {
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountain100DtoList.get(i).getMountainId());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                for (MountainComment mountainComment : mountainComments) {
                    star += mountainComment.getStar();
                }
                starAvr = (float) star / mountainComments.size();
            }
            SearchDto mountainInfo = new SearchDto(starAvr,mountain100DtoList.get(i));

            searchDto.add(mountainInfo);
        }
        for (SearchDto dto : searchDto) {
            System.out.println("dto.getMountain() = " + dto.getMountain());
        }
    }

    @Test
    @DisplayName("10개 랜덤으로 보여주기 query")
    void searchBefore2() {
        List<SearchDto> searchDtoList = new ArrayList<>();
        List<Map<String, Object>> mountainList = mountainRepository.beforeSearch();
        for (int i = 0; i < mountainList.size(); i++) {
            SearchDto searchDto = new SearchDto(mountainList.get(i));
            searchDtoList.add(searchDto);
        }
        for (Map<String, Object> stringObjectMap : mountainList) {
            System.out.println("stringObjectMap.get(\"mountain\") = " + stringObjectMap.get("mountain"));
        }
    }

    @Test
    @DisplayName("검색 후 페이지 jpa")
    void searchAfter() {
        String keyword = "악";
        int pageNum = 1;
        List<Mountain> mountainList = mountainRepository.searchAllByMountain(keyword);
        List<SearchDto> searchDtoList = new ArrayList<>();
        Pageable pageable = getPageable(pageNum);
        setSearchList(mountainList, searchDtoList);

        int start = pageNum * 5;
        int end = Math.min((start + 5), mountainList.size());

        Page<SearchDto> page = new PageImpl<>(searchDtoList.subList(start, end), pageable, searchDtoList.size());
        for (SearchDto searchDto : page) {
            System.out.println("searchDto.getMountain() = " + searchDto.getMountain());
        }
    }

    private void setSearchList(List<Mountain> mountainList, List<SearchDto> searchDtoList) {
        int star = 0;
        Double starAvr;
        for (Mountain mountain : mountainList) {
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountain.getMountainId());
            if (mountainComments.size() == 0) {
                starAvr = 0D;
            } else {
                for (int i = 0; i < mountainComments.size(); i++) {
                    star += mountainComments.get(i).getStar();
                }
                starAvr = (double) star / mountainComments.size();
            }

            SearchDto searchDto = new SearchDto(
                    mountain.getMountainId(),
                    mountain.getMountain(),
                    mountain.getMountainAddress(),
                    mountain.getMountainImgUrl(),
                    starAvr,
                    mountain.getLat(),
                    mountain.getLng()
            );
            searchDtoList.add(searchDto);
        }
    }

    private Pageable getPageable(int pageNum) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNum, 5, sort);
    }

    @Test
    @DisplayName("검색 후 페이지 Querydsl")
    void searchAfter2() {
        int pageNum=1;
        String keyword = "악";
        PageRequest pageRequest = PageRequest.of(pageNum, 5);
        Page<SearchDto> searchDtos = mountainRepository.searchPage(keyword, pageRequest);
        for (SearchDto searchDto : searchDtos) {
            System.out.println("searchDto.getMountain() = " + searchDto.getMountain());
        }


    }
}
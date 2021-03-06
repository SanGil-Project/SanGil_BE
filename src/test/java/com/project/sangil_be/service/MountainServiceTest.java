// package com.project.sangil_be.service;

 import com.project.sangil_be.feed.dto.FeedCommentResDto;
 import com.project.sangil_be.model.User;
 import com.project.sangil_be.login.repository.UserRepository;
 import com.project.sangil_be.mainpage.dto.NearbyMountainListDto;
 import com.project.sangil_be.model.*;
 import com.project.sangil_be.mountain.dto.BookMarkResponseDto;
 import com.project.sangil_be.mountain.dto.CommentListDto;
 import com.project.sangil_be.mountain.dto.SearchDto;
 import com.project.sangil_be.mountain.repository.BookMarkRepository;
 import com.project.sangil_be.mountain.repository.MountainCommentRepository;
 import com.project.sangil_be.mountain.repository.MountainRepository;
 import com.project.sangil_be.mypage.dto.CompletedListDto;
 import com.project.sangil_be.mypage.dto.TitleDto;
 import com.project.sangil_be.mypage.dto.UserTitleDto;
 import com.project.sangil_be.mypage.repository.CompletedRepository;
 import com.project.sangil_be.mypage.repository.GetTitleRepository;
 import com.project.sangil_be.mypage.repository.UserTitleRepository;
 import com.project.sangil_be.party.dto.PartyListDto;
 import com.project.sangil_be.etc.utils.Direction;
 import com.project.sangil_be.etc.utils.GeometryUtil;
 import com.project.sangil_be.etc.dto.LocationDto;
 import com.project.sangil_be.etc.utils.TitleUtil;
 import com.querydsl.core.QueryResults;
 import com.querydsl.core.types.Projections;
 import com.querydsl.jpa.impl.JPAQueryFactory;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.data.domain.*;

 import javax.persistence.EntityManager;
 import java.text.ParseException;
 import java.time.LocalDate;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;

 import static com.project.sangil_be.model.QBookMark.*;
 import static com.project.sangil_be.model.QFeedComment.*;
 import static com.project.sangil_be.model.QMountain.mountain1;
 import static com.project.sangil_be.model.QMountainComment.mountainComment1;
 import static com.project.sangil_be.model.QParty.*;
 import static com.project.sangil_be.model.QUser.user;
 import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest
// class MountainServiceTest {
//
//     private final MountainRepository mountainRepository;
//     private final MountainCommentRepository mountainCommentRepository;
//     private final BookMarkRepository bookMarkRepository;
//     private final TitleUtil titleService;
//     private final GetTitleRepository getTitleRepository;
//     private final UserTitleRepository userTitleRepository;
//     private final UserRepository userRepository;
//     private final CompletedRepository completedRepository;
//
//     @Autowired
//     MountainServiceTest(MountainRepository mountainRepository, MountainCommentRepository mountainCommentRepository, BookMarkRepository bookMarkRepository, TitleUtil titleService, GetTitleRepository getTitleRepository, UserTitleRepository userTitleRepository, UserRepository userRepository, CompletedRepository completedRepository) {
//         this.mountainRepository = mountainRepository;
//         this.mountainCommentRepository = mountainCommentRepository;
//         this.bookMarkRepository = bookMarkRepository;
//         this.titleService = titleService;
//         this.getTitleRepository = getTitleRepository;
//         this.userTitleRepository = userTitleRepository;
//         this.userRepository = userRepository;
//         this.completedRepository=completedRepository;
//     }
//
//     @Autowired
//     EntityManager em;
//     JPAQueryFactory queryFactory;
//
//     @BeforeEach
//     public void before() {
//         queryFactory = new JPAQueryFactory(em);
//     }

//     @Test
//     void searchMountain() {
//         String keyword = "???";
//         PageRequest pageable = PageRequest.of(0, 5);
//
//         Page<SearchDto> searchDtos = mountainRepository.searchPage(keyword, pageable);
//         List<String> mountains = new ArrayList<>();
//         mountains.add("?????????");
//         mountains.add("?????????");
//         mountains.add("?????????");
//         mountains.add("?????????");
//         mountains.add("?????????");
//
//         for (int i = 0; i < searchDtos.getSize(); i++) {
//             assertThat(searchDtos.getContent().get(i).getMountain()).isEqualTo(mountains.get(i));
//         }
//     }
//
//     @Test
//     void searchMountain2() {
//         String keyword = "???";
//         QueryResults<SearchDto> results = queryFactory
//                 .select(Projections.constructor(SearchDto.class,
//                         mountain1.mountainId,
//                         mountain1.mountain,
//                         mountain1.mountainAddress,
//                         mountain1.mountainImgUrl,
//                         mountainComment1.star.avg().as("starAvr"),
//                         mountain1.lat,
//                         mountain1.lng))
//                 .from(mountain1)
//                 .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
//                 .where(mountain1.mountain.contains(keyword).or(mountain1.mountainAddress.contains(keyword)))
//                 .groupBy(mountain1.mountainId)
//                 .offset(0)
//                 .limit(5)
//                 .fetchResults();
//
//         List<SearchDto> content = results.getResults();
//         long limit = results.getLimit();
//         List<String> mountains = new ArrayList<>();
//         mountains.add("?????????");
//         mountains.add("?????????");
//         mountains.add("?????????");
//         mountains.add("?????????");
//         mountains.add("?????????");
//
//         for (int i = 0; i < limit; i++) {
//             assertThat(content.get(i).getMountain()).isEqualTo(mountains.get(i));
//         }
//
//     }
//
//     @Test
//     void searchMountain3() {
//         Long mountainId = 1L;
//         QueryResults<CommentListDto> results = queryFactory
//                 .select(Projections.constructor(CommentListDto.class,
//                         mountainComment1.mountainCommentId,
//                         mountainComment1.mountainComment,
//                         user.userId,
//                         user.nickname,
//                         user.userTitle,
//                         mountainComment1.star,
//                         mountainComment1.createdAt))
//                 .from(mountainComment1)
//                 .join(user).on(mountainComment1.userId.eq(user.userId))
//                 .where(mountainComment1.mountainId.eq(mountainId))
//                 .offset(0)
//                 .limit(5)
//                 .fetchResults();
//
//         List<CommentListDto> content = results.getResults();
//         for (CommentListDto commentListDto : content) {
//             System.out.println("commentListDto = " + commentListDto);
//         }
//     }
//
//     @Test
//     void searchParty() throws ParseException {
//         String keyword = "??????";
//         QueryResults<PartyListDto> results = queryFactory
//                 .select(Projections.constructor(PartyListDto.class,
//                         party.partyId,
//                         party.user.nickname,
//                         party.user.userTitle,
//                         party.mountain,
//                         party.address,
//                         party.partyDate,
//                         party.partyTime,
//                         party.maxPeople,
//                         party.curPeople,
//                         party.completed,
//                         party.createdAt))
//                 .from(party)
//                 .where(party.mountain.contains(keyword)
//                         .or(party.address.contains(keyword)
//                                 .or(party.title.contains(keyword))))
//                 .offset(0)
//                 .limit(6)
//                 .fetchResults();
//         List<PartyListDto> content = results.getResults();
//         LocalDate date = LocalDate.now();
//         for (PartyListDto partyListDto : content) {
//             LocalDate date1 = LocalDate.parse(partyListDto.getPartyDate());
//             if (date1.isBefore(date)) {
//                 partyListDto.setPartyDate("?????????????????????.");
//             }
//             System.out.println("partyListDto = " + partyListDto.getPartyDate());
//         }
//     }
//
//     @Test
//     void myPageBookmark() {
//         Long userId = 4L;
//         QueryResults<BookMarkResponseDto> results = queryFactory
//                 .select(Projections.constructor(BookMarkResponseDto.class,
//                         mountain1.mountainId,
//                         mountain1.mountain,
//                         mountain1.mountainAddress,
//                         mountainComment1.star.avg().as("starAvr")))
//                 .from(bookMark)
//                 .leftJoin(mountain1).on(mountain1.mountainId.eq(bookMark.mountainId))
//                 .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
//                 .where(bookMark.userId.eq(userId))
//                 .groupBy(mountain1.mountainId)
//                 .offset(0)
//                 .limit(5)
//                 .fetchResults();
//         List<BookMarkResponseDto> content = results.getResults();
//         for (BookMarkResponseDto bookMarkResponseDto : content) {
//             System.out.println(bookMarkResponseDto.getMountain());
//         }
//         long total = results.getTotal();
//         System.out.println(total);
//     }
//
//     @Test
//     void nearByMountain() {
//         double lat = 36.7683406;
//         double lng = 126.4457861;
//         double distance = 7; // km ?????? // ?????? ?????? 5km ????????? ?????? ???
//
//         LocationDto northEast = GeometryUtil.calculate(lat, lng, distance, Direction.NORTHEAST.getBearing());
//         LocationDto southWest = GeometryUtil.calculate(lat, lng, distance, Direction.SOUTHWEST.getBearing());
//
//         double x1 = northEast.getLat();
//         double y1 = northEast.getLng();
//         double x2 = southWest.getLat();
//         double y2 = southWest.getLng();
//
//         QueryResults<NearbyMountainListDto> results = queryFactory
//                 .select(Projections.constructor(NearbyMountainListDto.class,
//                         mountain1.mountainId,
//                         mountain1.mountain,
//                         mountain1.mountainImgUrl,
//                         mountain1.mountainAddress,
//                         mountainComment1.star.avg().as("starAvr")))
//                 .from(mountain1)
//                 .leftJoin(mountainComment1).on(mountainComment1.mountainId.eq(mountain1.mountainId))
//                 .where(mountain1.lat.between(x2, x1).and(mountain1.lng.between(y2, y1)))
//                 .groupBy(mountain1.mountainId)
//                 .offset(0)
//                 .limit(7)
//                 .fetchResults();
//
//         List<NearbyMountainListDto> content = results.getResults();
//         for (NearbyMountainListDto nearbyMountainListDto : content) {
//             System.out.println("nearbyMountainListDto.getMountainName() = " + nearbyMountainListDto.getMountainName());
//         }
//     }
//
// //    @Test
// //    void mainfeed() {
// //        Long userId = 1L;
// //        QueryResults<FeedResponseDto> results = queryFactory
// //                .select(Projections.constructor(FeedResponseDto.class,
// //                        feed.user.userId,
// //                        feed.feedId,
// //                        feed.user.nickname,
// //                        feed.user.userTitle,
// //                        feed.user.userImgUrl,
// //                        feed.feedImgUrl,
// //                        feed.feedContent,
// //                        feed.createdAt,
// //                        good.count(),
// //                        JPAExpressions.selectOne().from(good).where(good.userId.eq(userId)).fetchAll().exists()).as("goodStatus"))
// //                .from(feed)
// //                .leftJoin(good).on(feed.feedId.eq(good.feedId))
// //                .where(good.feedId.eq(feed.feedId))
// //                .orderBy(feed.createdAt.desc())
// //                .limit(7)
// //                .fetchResults();
// //
// //        List<FeedResponseDto> content = results.getResults();
// //        for (FeedResponseDto feedResponseDto : content) {
// //            System.out.println("feedResponseDto.getGoodCnt() = " + feedResponseDto.getGoodCnt());
// //        }
// //
// //    }
//
//     @Test
//     void title() {
//         User user = userRepository.findByUserId(1L);
//         List<TitleDto> titleDtoList = titleService.getAllTitle(user);
//         List<UserTitle> userTitles = userTitleRepository.findAll();
//         List<GetTitle> getTitles = getTitleRepository.findAllByUser(user);
//
//         HashMap<String, Boolean> title = new HashMap<>();
//
//
//         for (int i = 0; i < userTitles.size(); i++) {
//             title.put(userTitles.get(i).getUserTitle(), false);
//             for (int j = 0; j < getTitles.size(); j++) {
//                 if (userTitles.get(i).getUserTitle().equals(getTitles.get(j).getUserTitle())) {
//                     title.replace(userTitles.get(i).getUserTitle(), false, true);
//                 }
//             }
//         }
//         List<UserTitleDto> userTitleDtos = new ArrayList<>();
//         for (String s : title.keySet()) {
//             UserTitle userTitle = userTitleRepository.findByUserTitle(s);
//             if (title.get(s) == true) {
//                 if (s.equals(user.getUserTitle())) {
//                     UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getCTitleImgUrl(), title.get(userTitle.getUserTitle()));
//                     userTitleDtos.add(userTitleDto);
//                 } else {
//                     UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getBTitleImgUrl(), title.get(userTitle.getUserTitle()));
//                     userTitleDtos.add(userTitleDto);
//                 }
//             } else {
//                 UserTitleDto userTitleDto = new UserTitleDto(userTitle, userTitle.getQTitleImgUrl(), title.get(userTitle.getUserTitle()));
//                 userTitleDtos.add(userTitleDto);
//             }
//         }
//         System.out.println(userTitleDtos.size());
//
//
// //        UserTitleResponseDto userTitleResponseDto = new UserTitleResponseDto(userTitleDtos, titleDtoList);
//
//     }
//     @Test
//     public void commentList() {
//         Long feedId = 1L;
//         QueryResults<FeedCommentResDto> results = queryFactory
//                 .select(Projections.constructor(FeedCommentResDto.class,
//                         feedComment1.feedCommentId,
//                         feedComment1.user.userId,
//                         feedComment1.user.nickname,
//                         feedComment1.user.userTitle,
//                         feedComment1.user.userImgUrl,
//                         feedComment1.feedComment))
//                 .from(feedComment1)
//                 .where(feedComment1.feed.feedId.eq(feedId))
//                 .offset(0)
//                 .limit(7)
//                 .fetchResults();
//         List<FeedCommentResDto> content = results.getResults();
//         for (FeedCommentResDto feedCommentResDto : content) {
//             System.out.println("feedCommentResDto.getFeedComment() = " + feedCommentResDto.getFeedComment());
//         }
//         long total = results.getTotal();
//     }
//
//     @Test
//     public void trakingtest(){
//         Long userId = 2L;
//
//         List<Completed> completed = completedRepository.findAllByUserId(userId);
//         List<CompletedListDto> completedListDtos = new ArrayList<>();
//         for (Completed complete : completed) {
//             if(complete.getTotalDistance() == 0) {
//                 System.out.println("???????????? ?????? ??????");
//             }else {
//                 Mountain mountain = mountainRepository.findByMountainId(complete.getMountainId());
//                 CompletedListDto completedListDto = new CompletedListDto(complete, mountain);
//                 completedListDtos.add(completedListDto);
//             }
//         }
//         for (CompletedListDto completedListDto : completedListDtos){
//             System.out.println("completedListDto = " + completedListDto.getTotalDistance());
//         }
//     }
//
//     @Test
//     public void searchparty(){
//            String keyword = "???";
//
//         QueryResults<PartyListDto> results = queryFactory
//                 .select(Projections.constructor(PartyListDto.class,
//                         party.partyId,
//                         party.user.nickname,
//                         party.title,
//                         party.mountain,
//                         party.address,
//                         party.partyDate,
//                         party.partyTime,
//                         party.maxPeople,
//                         party.curPeople))
//                 .from(party)
//                 .where(party.mountain.contains(keyword)
//                         .or(party.address.contains(keyword)
//                                 .or(party.title.contains(keyword))))
//                 .offset(0)
//                 .limit(5)
//                 .fetchResults();
//         List<PartyListDto> content = results.getResults();
//         for (PartyListDto partyListDto : content) {
//
//             System.out.println("partyListDto = " + partyListDto.getPartyDate());
//
//         }
//
//     }

// }

package com.project.sangil_be.service;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.*;
import com.project.sangil_be.repository.*;
import com.project.sangil_be.utils.Calculator;
import com.project.sangil_be.utils.DistanceToUser;
import com.project.sangil_be.utils.TitleUtil;
import com.project.sangil_be.utils.Validator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import java.util.*;

@SpringBootTest
public class NPlus1Test {

    private final MountainRepository mountainRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final BookMarkRepository bookMarkRepository;
    private final TitleUtil titleService;
    private final GetTitleRepository getTitleRepository;
    private final UserTitleRepository userTitleRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final Validator validator;
    private final Calculator calculator;
    private final AttendRepository attendRepository;

    @Autowired
    NPlus1Test(MountainRepository mountainRepository, MountainCommentRepository mountainCommentRepository, BookMarkRepository bookMarkRepository, TitleUtil titleService, GetTitleRepository getTitleRepository, UserTitleRepository userTitleRepository, UserRepository userRepository, PartyRepository partyRepository, Validator validator, Calculator calculator, AttendRepository attendRepository) {
        this.mountainRepository = mountainRepository;
        this.mountainCommentRepository = mountainCommentRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.titleService = titleService;
        this.getTitleRepository = getTitleRepository;
        this.userTitleRepository = userTitleRepository;
        this.userRepository = userRepository;
        this.partyRepository = partyRepository;
        this.validator = validator;
        this.calculator = calculator;
        this.attendRepository = attendRepository;
    }

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @Order(1)
    @DisplayName("10개 랜덤으로 보여주기 jpa")
    public void searchBefore() {
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
            SearchDto mountainInfo = new SearchDto(starAvr, mountain100DtoList.get(i));

            searchDto.add(mountainInfo);
        }
        for (SearchDto dto : searchDto) {
            System.out.println("dto.getMountain() = " + dto.getMountain());
        }
    }

    @Test
    @Order(2)
    @DisplayName("10개 랜덤으로 보여주기 query")
    public void searchBefore_2() {
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
    @Order(3)
    @DisplayName("검색 후 페이지 jpa")
    public void searchAfter() {
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

    @Test
    @Order(4)
    @DisplayName("검색 후 페이지 Querydsl")
    public void searchAfter_2() {
        int pageNum = 1;
        String keyword = "악";
        PageRequest pageRequest = PageRequest.of(pageNum, 5);
        Page<SearchDto> searchDtos = mountainRepository.searchPage(keyword, pageRequest);
        for (SearchDto searchDto : searchDtos) {
            System.out.println("searchDto.getMountain() = " + searchDto.getMountain());
        }
    }

    @Test
    @Order(5)
    @DisplayName("북마크 순 탑10 native-query")
    public void bookMarkTop10() {
        List<Map<String, Object>> top10Mountains = mountainRepository.methodlist();
        List<Top10MountainDto> testDtos = new ArrayList<>();
        for (int i = 0; i < top10Mountains.size(); i++) {
            Boolean bookMark = bookMarkRepository.existsByMountainIdAndUserId(Long.valueOf(String.valueOf(top10Mountains.get(i).get("mountain_id"))), 1l);
            Top10MountainDto top10MountainDto = new Top10MountainDto(top10Mountains.get(i), bookMark);
            testDtos.add(top10MountainDto);
        }
        for (Top10MountainDto testDto : testDtos) {
            System.out.println("testDto.getMountain() = " + testDto.getMountain());
        }
    }

    @Test
    @Order(6)
    @DisplayName("북마크 순 탑10 jpa")
    public void bookMarkTop10_2() {
        List<Mountain> mountainList = mountainRepository.findAll();

        List<Mountain10ResponseDto> mountain10ResponseDtos = new ArrayList<>();
        int star = 0;
        float starAvr;
        for (int i = 0; i < mountainList.size(); i++) {
            int bookMarkCnt = bookMarkRepository.countAllByMountainId(mountainList.get(i).getMountainId());
            Boolean bookMark = bookMarkRepository.existsByMountainIdAndUserId(mountainList.get(i).getMountainId(), 1l);
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountainList.get(i).getMountainId());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                for (int j = 0; j < mountainComments.size(); j++) {
                    star += mountainComments.get(j).getStar();
                }
                starAvr = (float) star / mountainComments.size();
            }
            Mountain10ResponseDto mountain10ResponseDto = new Mountain10ResponseDto(mountainList.get(i), String.format("%.1f", starAvr), bookMark, bookMarkCnt);
            mountain10ResponseDtos.add(mountain10ResponseDto);
        }

        Collections.sort(mountain10ResponseDtos, new CntComparator().reversed());

        List<Top10MountainDto> Top10MountainDtos = new ArrayList<>();

        int star2 = 0;
        float starAvr2;

        for (int i = 0; i < 10; i++) {
            boolean bookMark2 = bookMarkRepository.existsByMountainIdAndUserId(mountain10ResponseDtos.get(i).getMountainId(), 1l);
            List<MountainComment> mountainComments2 = mountainCommentRepository.findAllByMountainId(mountain10ResponseDtos.get(i).getMountainId());
            if (mountainComments2.size() == 0) {
                starAvr2 = 0;
            } else {
                for (int j = 0; j < mountainComments2.size(); j++) {
                    star += mountainComments2.get(j).getStar();
                }
                starAvr2 = (float) star2 / mountainComments2.size();
            }

            Top10MountainDto mountain10ResponseDto = new Top10MountainDto(mountain10ResponseDtos.get(i), String.format("%.1f", starAvr2), bookMark2);
            Top10MountainDtos.add(mountain10ResponseDto);

        }

        for (Top10MountainDto top10MountainDto : Top10MountainDtos) {
            System.out.println("top10MountainDto.getMountain() = " + top10MountainDto.getMountain());
        }
    }

    private class CntComparator implements Comparator<Mountain10ResponseDto> {
        @Override
        public int compare(Mountain10ResponseDto t1, Mountain10ResponseDto t2) {
            if (t1.getBookMarkCnt() > t2.getBookMarkCnt()) {
                return 1;
            } else if (t1.getBookMarkCnt() < t2.getBookMarkCnt()) {
                return -1;
            }
            return 0;
        }
    }

    @Test
    @Order(7)
    @DisplayName("즐겨찾기한 산 querydsl")
    public void bookMarkMountain() {
        int pageNum = 0;
        double lat = 36.7683406;
        double lng = 126.4457861;
        PageRequest pageRequest = PageRequest.of(pageNum, 6);
        Page<BookMarkResponseDto> bookMarkResponseDtos = bookMarkRepository.bookMarkMountain(1l, pageRequest);
        for (BookMarkResponseDto bookMarkResponseDto : bookMarkResponseDtos) {
            boolean bookMarkChk = bookMarkRepository.existsByMountainIdAndUserId(bookMarkResponseDto.getMountainId(), 1l);
            Mountain mountain = mountainRepository.findByMountainId(bookMarkResponseDto.getMountainId());
            Double distance = DistanceToUser.distance(lat, lng, mountain.getLat(), mountain.getLng(), "kilometer");
            bookMarkResponseDto.setBookMarkChk(bookMarkChk);
            bookMarkResponseDto.setDistance(Math.round(distance * 100) / 100.0);
        }
        for (BookMarkResponseDto bookMarkResponseDto : bookMarkResponseDtos) {
            System.out.println("bookMarkResponseDto.getMountainId() = " + bookMarkResponseDto.getMountainId());
        }
    }

    @Test
    @Order(8)
    @DisplayName("즐겨찾기한 산 jpa")
    public void bookMarkMountain2() {
        double lat = 36.7683406;
        double lng = 126.4457861;
        List<BookMark> bookMarkList = bookMarkRepository.findAllByUserId(1l);
        List<BookMarkResponseDto> bookMarkResponseDtos = new ArrayList<>();

        for (BookMark bookMark : bookMarkList) {
            boolean bookMarkChk = bookMarkRepository.existsByMountainIdAndUserId(bookMark.getMountainId(),
                    1l);
            Mountain mountain = mountainRepository.findById(bookMark.getMountainId()).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 산이 없습니다.")
            );

            //유저와 즐겨찾기한 산과의 거리 계산
            Double distance = DistanceToUser.distance(lat, lng, mountain.getLat(),
                    mountain.getLng(), "kilometer");

            int star = 0;
            Double starAvr = 0d;
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(bookMark.getMountainId());
            for (int i = 0; i < mountainComments.size(); i++) {
                if (mountainComments.size() == 0) {
                    starAvr = 0d;
                } else {
                    star += mountainComments.get(i).getStar();
                    starAvr = (double) star / mountainComments.size();
                }
            }
            bookMarkResponseDtos.add(new BookMarkResponseDto(mountain, bookMarkChk, starAvr, distance));
        }
        for (BookMarkResponseDto bookMarkResponseDto : bookMarkResponseDtos) {
            System.out.println("bookMarkResponseDto.getMountainName() = " + bookMarkResponseDto.getMountainName());
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
}

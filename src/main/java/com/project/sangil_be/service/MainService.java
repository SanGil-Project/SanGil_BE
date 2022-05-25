package com.project.sangil_be.service;

import com.project.sangil_be.utils.*;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.*;
import com.project.sangil_be.repository.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MainService {

    private final PartyRepository partyRepository;
    private final MountainRepository mountainRepository;
    private final BookMarkRepository bookMarkRepository;
    private final AttendRepository attendRepository;
    private final FeedRepository feedRepository;
    private final GoodRepository goodRepository;
    private final TitleService titleService;
    private final MountainCommentRepository mountainCommentRepository;
    private final Calculator calculator;

    // 메인/마이페이지 예정된 등산 모임 임박한 순
    @Transactional
    public PlanResponseDto getPlan(UserDetailsImpl userDetails) {
        List<Attend> attend = attendRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<Party> partyList = new ArrayList<>();

        for (int i = 0; i < attend.size(); i++) {
            partyList.add(partyRepository.findByPartyIdOrderByPartyDateAsc(attend.get(i).getPartyId()));
        }
        Collections.sort(partyList, new PartyDateComparator());

        List<PlanListDto> planListDtos = new ArrayList<>();
        for (Party party : partyList) {
            LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
            String year = String.valueOf(now).split("-")[0];
            String month = String.valueOf(now).split("-")[1];
            String day = String.valueOf(now).split("-")[2];
            String date = year + "-" + month + "-" + day;

            int compare = date.compareTo(String.valueOf(party.getPartyDate()));

            String msg;
            if (compare > 0) {
                attendRepository.deleteByPartyIdAndUserId(party.getPartyId(), userDetails.getUser().getUserId());
                msg = "false";
            } else {
                msg = "true";
            }
            planListDtos.add(new PlanListDto(party, msg));
        }

        return new PlanResponseDto(planListDtos);
    }

    // 메인페이지 최신 2개 모임
    public TwoPartyListResponseDto getTwoParty() {
        List<Party> partyList = partyRepository.findAllByOrderByCreatedAtDesc();
        List<TwoPartyListDto> partyListDtos = new ArrayList<>();
        if (partyList.size() < 3) {
            for (int i = 0; i < partyList.size(); i++) {
                partyListDtos.add(new TwoPartyListDto(partyList.get(i)));
            }
        } else {
            for (int i = 0; i < 2; i++) {
                partyListDtos.add(new TwoPartyListDto(partyList.get(i)));
            }
        }
        return new TwoPartyListResponseDto(partyListDtos);
    }

    // 메인페이지 북마크 순으로 탑10
    public List<Top10MountainDto> get10Mountains(UserDetailsImpl userDetails) {
        List<Map<String, Object>> top10Mountains = mountainRepository.methodlist();
        List<Top10MountainDto> testDtos = new ArrayList<>();
        for (int i = 0; i < top10Mountains.size(); i++) {
            Boolean bookMark = bookMarkRepository.existsByMountainIdAndUserId(Long.valueOf(String.valueOf(top10Mountains.get(i).get("mountain_id"))), userDetails.getUser().getUserId());
            Top10MountainDto top10MountainDto = new Top10MountainDto(top10Mountains.get(i), bookMark);
            testDtos.add(top10MountainDto);
        }
        return testDtos;
    }

    // 쿼리로 고칠것
    // 메인 페이지 피드 7개
    public FeedListResponseDto mainfeeds(UserDetailsImpl userDetails) {
        List<Feed> feed = feedRepository.findAllByOrderByCreatedAtDesc();
        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
        if (feed.size() < 8) {
            for (int i = 0; i < feed.size(); i++) {
                boolean goodStatus;
                int goodCnt = goodRepository.findByFeedId(feed.get(i).getFeedId()).size();
                long beforeTime = ChronoUnit.MINUTES.between(feed.get(i).getCreatedAt(), LocalDateTime.now());
                try {
                    goodStatus = goodRepository.existsByFeedIdAndUserId(feed.get(i).getFeedId(), userDetails.getUser().getUserId());
                } catch (Exception e) {
                    goodStatus = false;
                }
                feedResponseDtos.add(new FeedResponseDto(feed.get(i), goodCnt, goodStatus,calculator.time(beforeTime)));
            }
        } else {
            for (int i = 0; i < 7; i++) {
                boolean goodStatus;
                int goodCnt = goodRepository.findByFeedId(feed.get(i).getFeedId()).size();
                long beforeTime = ChronoUnit.MINUTES.between(feed.get(i).getCreatedAt(), LocalDateTime.now());
                try {
                    goodStatus = goodRepository.existsByFeedIdAndUserId(feed.get(i).getFeedId(), userDetails.getUser().getUserId());
                } catch (Exception e) {
                    goodStatus = false;
                }
                feedResponseDtos.add(new FeedResponseDto(feed.get(i), goodCnt, goodStatus, calculator.time(beforeTime)));
            }
        }

        List<TitleDto> titleDtoList = titleService.getGoodTitle(userDetails);
        return new FeedListResponseDto(feedResponseDtos, titleDtoList);
    }

    private Pageable getPageable(int pageNum) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNum, 15, sort);
    }

//    // 자기 주변 산
//    public NearbyMountainDto nearby(double lat, double lng, int pageNum, UserDetailsImpl userDetails) {
//
//        double distance = 7; // km 단위 // 대략 반경 5km 이내의 주변 산
//
//        Location northEast = GeometryUtil.calculate(lat, lng, distance, Direction.NORTHEAST.getBearing());
//        Location southWest = GeometryUtil.calculate(lat, lng, distance, Direction.SOUTHWEST.getBearing());
//
//        double x1 = northEast.getLat();
//        double y1 = northEast.getLng();
//        double x2 = southWest.getLat();
//        double y2 = southWest.getLng();
//
//        List<Mountain> mountains = mountainRepository.findAll(x2, x1, y2, y1);
//        List<NearbyMountainListDto> nearbyMountainListDtos = new ArrayList<>();
//        int star = 0;
//        double starAvr = 0;
//        for (Mountain mountain : mountains) {
//            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountain.getMountainId());
//            if (mountainComments.size() == 0) {
//                starAvr = 0;
//            } else {
//                for (int i = 0; i < mountainComments.size(); i++) {
//                    star += mountainComments.get(i).getStar();
//                }
//                starAvr = (float) star / mountainComments.size();
//            }
//            Boolean bookmark = bookMarkRepository.existsByMountainIdAndUserId(mountain.getMountainId(), userDetails.getUser().getUserId());
//            double dis = DistanceToUser.distance(lat, lng, mountain.getLat(), mountain.getLng(), "kilometer");
//            NearbyMountainListDto nearbyMountainListDto = new NearbyMountainListDto(mountain, starAvr, bookmark, dis);
//            nearbyMountainListDtos.add(nearbyMountainListDto);
//        }
//        Pageable pageable = getPageable(pageNum);
//        int start = pageNum * 7;
//        int end = Math.min((start + 7), mountains.size());
//        Page<NearbyMountainListDto> page = new PageImpl<>(nearbyMountainListDtos.subList(start, end), pageable, nearbyMountainListDtos.size());
//        return new NearbyMountainDto(page);
//    }

    // 동적쿼리 // 시간 비슷함함    // 자기 주변 산
    public NearbyMountainDto nearby(double lat, double lng, int pageNum, UserDetailsImpl userDetails) {

        double distance = 7; // km 단위 // 대략 반경 5km 이내의 주변 산

        Location northEast = GeometryUtil.calculate(lat, lng, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(lat, lng, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLat();
        double y1 = northEast.getLng();
        double x2 = southWest.getLat();
        double y2 = southWest.getLng();

        PageRequest pageRequest = PageRequest.of(pageNum, 7);
        Page<NearbyMountainListDto> nearbyMountainListDtos = mountainRepository.nearByMountain(x1, x2, y1, y2, pageRequest);
        for (NearbyMountainListDto nearbyMountainListDto : nearbyMountainListDtos) {
            Mountain mountain = mountainRepository.findByMountainId(nearbyMountainListDto.getMountainId());
            Boolean bookmark = bookMarkRepository.existsByMountainIdAndUserId(mountain.getMountainId(), userDetails.getUser().getUserId());
            double dis = DistanceToUser.distance(lat, lng, mountain.getLat(), mountain.getLng(), "kilometer");
            nearbyMountainListDto.setBookmark(bookmark);
            nearbyMountainListDto.setDistance(dis);
        }

        return new NearbyMountainDto(nearbyMountainListDtos);
    }

    private class PartyDateComparator implements Comparator<Party> {
        @Override
        public int compare(Party d1, Party d2) {
            return d1.getPartyDate().compareTo(d2.getPartyDate());
        }
    }
}
//    public List<Top10MountainDto> get10Mountains(UserDetailsImpl userDetails) {
//        List<Mountain> mountainList = mountainRepository.findAll();
//
//        List<Mountain10ResponseDto> mountain10ResponseDtos = new ArrayList<>();
//        int star = 0;
//        float starAvr;
//        for (int i = 0; i < mountainList.size(); i++) {
//            int bookMarkCnt = bookMarkRepository.countAllByMountainId(mountainList.get(i).getMountainId());
//            Boolean bookMark = bookMarkRepository.existsByMountainIdAndUserId(mountainList.get(i).getMountainId(), userDetails.getUser().getUserId());
//            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountainList.get(i).getMountainId());
//            if (mountainComments.size() == 0) {
//                starAvr = 0;
//            } else {
//                for (int j = 0; j< mountainComments.size(); j++) {
//                    star += mountainComments.get(j).getStar();
//                }
//                starAvr = (float) star / mountainComments.size();
//            }
//            Mountain10ResponseDto mountain10ResponseDto = new Mountain10ResponseDto(mountainList.get(i), String.format("%.1f", starAvr), bookMark, bookMarkCnt);
//            mountain10ResponseDtos.add(mountain10ResponseDto);
//        }
//
//        Collections.sort(mountain10ResponseDtos, new CntComparator().reversed());
//
//        List<Top10MountainDto> Top10MountainDtos = new ArrayList<>();
//
//        int star2 = 0;
//        float starAvr2;
//
//        for (int i = 0; i < 10; i++) {
//            boolean bookMark2 = bookMarkRepository.existsByMountainIdAndUserId(mountain10ResponseDtos.get(i).getMountainId(), userDetails.getUser().getUserId());
//            List<MountainComment> mountainComments2 = mountainCommentRepository.findAllByMountainId(mountain10ResponseDtos.get(i).getMountainId());
//            if (mountainComments2.size() == 0) {
//                starAvr2 = 0;
//            } else {
//                for (int j = 0; j< mountainComments2.size(); j++) {
//                    star += mountainComments2.get(j).getStar();
//                }
//                starAvr2 = (float) star2 / mountainComments2.size();
//            }
//
//            Top10MountainDto mountain10ResponseDto = new Top10MountainDto(mountain10ResponseDtos.get(i), String.format("%.1f", starAvr2), bookMark2);
//            Top10MountainDtos.add(mountain10ResponseDto);
//
//        }
//
//        return Top10MountainDtos;
//    }

//    private class CntComparator implements Comparator<Mountain10ResponseDto> {
//        @Override
//        public int compare(Mountain10ResponseDto t1, Mountain10ResponseDto t2) {
//            if (t1.getBookMarkCnt() > t2.getBookMarkCnt()) {
//                return 1;
//            } else if (t1.getBookMarkCnt() < t2.getBookMarkCnt()) {
//                return -1;
//            }
//            return 0;
//        }
//    }

//
//private class PartyDateComparator implements Comparator<Party> {
//    @Override
//    public int compare(Party d1, Party d2) {
//        return d1.getPartyDate().compareTo(d2.getPartyDate());
//    }
//}

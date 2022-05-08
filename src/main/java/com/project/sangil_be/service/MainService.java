package com.project.sangil_be.service;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.*;
import com.project.sangil_be.repository.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {

    private final PartyRepository partyRepository;
    private final Mountain100Repository mountain100Repository;
    private final MountainCommentRepository mountainCommentRepository;
    private final BookMarkRepository bookMarkRepository;
    private final AttendRepository attendRepository;
    private final FeedRepository feedRepository;
    private final GoodRepository goodRepository;

    @Transactional
    public PlanResponseDto getPlan(UserDetailsImpl userDetails) {
        List<Attend> attend = attendRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<Party> partyList = new ArrayList<>();

        for(int i = 0; i<attend.size(); i++){
            partyList.add(partyRepository.findByPartyIdOrderByPartyDateAsc(attend.get(i).getPartyId()));
        }
        Collections.sort(partyList, new PartyDateComparator());

        List<PlanListDto> planListDtos = new ArrayList<>();
        for (Party party : partyList) {
            LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
            String year = String.valueOf(now).split("-")[0];
            String month = String.valueOf(now).split("-")[1];
            String day = String.valueOf(now).split("-")[2];
            String date = year+"-"+month+"-"+day;
            int compare = date.compareTo(party.getPartyDate());
            String msg;
            if (compare > 0) {
                attendRepository.deleteByPartyIdAndUserId(party.getPartyId(),userDetails.getUser().getUserId());
                msg = "false";
            } else {
                msg = "true";
            }
            planListDtos.add(new PlanListDto(party,msg));
        }
        return new PlanResponseDto(planListDtos);
    }

    @Transactional
    public TwoPartyListResponseDto getTwoParty() {
        List<Party> partyList = partyRepository.findAllByOrderByCreatedAtDesc();
        List<TwoPartyListDto> partyListDtos = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            partyListDtos.add(new TwoPartyListDto(partyList.get(i)));
        }

        return new TwoPartyListResponseDto(partyListDtos);
    }

    // DTO 하나 더 만들어서 mountainList 로 내보내기
    // 수정 필요
    public List<Top10MountainDto> get10Mountains(UserDetailsImpl userDetails) {
        List<Mountain100> mountain100List = mountain100Repository.findAll();
        List<Mountain10ResponseDto> mountain10ResponseDtos = new ArrayList<>();

        int star=0;
        float starAvr=0;
        for (int i = 0; i < mountain100List.size(); i++) {
            int bookMarkCnt = bookMarkRepository.countAllByMountain100Id(mountain100List.get(i).getMountain100Id());
            boolean bookMark = bookMarkRepository.existsByMountain100IdAndUserId(mountain100List.get(i).getMountain100Id(),userDetails.getUser().getUserId());
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100Id(mountain100List.get(i).getMountain100Id());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                star+=mountainComments.get(i).getStar();
                starAvr = (float)star/mountainComments.size();
            }
            Mountain10ResponseDto mountain10ResponseDto = new Mountain10ResponseDto(mountain100List.get(i),String.format("%.1f",starAvr),bookMark,bookMarkCnt);
            mountain10ResponseDtos.add(mountain10ResponseDto);
        }
        Collections.sort(mountain10ResponseDtos, new CntComparator().reversed());

        List<Top10MountainDto> Top10MountainDtos = new ArrayList<>();
        int star2=0;
        float starAvr2=0;
        for (int i = 0; i < 10; i++) {
            boolean bookMark2 = bookMarkRepository.existsByMountain100IdAndUserId(mountain10ResponseDtos.get(i).getMountainId(),userDetails.getUser().getUserId());
            List<MountainComment> mountainComments2 = mountainCommentRepository.findAllByMountain100Id(mountain10ResponseDtos.get(i).getMountainId());
            if (mountainComments2.size() == 0) {
                starAvr2 = 0;
            } else {
                star2+=mountainComments2.get(i).getStar();
                starAvr2 = (float)star2/mountainComments2.size();
            }
            Top10MountainDto mountain10ResponseDto = new Top10MountainDto(mountain10ResponseDtos.get(i),String.format("%.1f",starAvr),bookMark2);
            Top10MountainDtos.add(mountain10ResponseDto);
        }

        return Top10MountainDtos;
    }

    public FeedListResponseDto mainfeeds(int pageNum, UserDetailsImpl userDetails) {

        List<Feed> feed = feedRepository.findAll();

        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();

        for (Feed feeds : feed){

            int goodCnt = goodRepository.findByFeedId(feeds.getFeedId()).size();
            boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feeds.getFeedId(),userDetails.getUser().getUserId());

            feedResponseDtos.add(new FeedResponseDto(feeds,goodCnt,goodStatus));

        }

        Pageable pageable = getPageable(pageNum);

        int start = pageNum * 15;
        int end = Math.min((start + 15), feed.size());

        Page<FeedResponseDto> page = new PageImpl<>(feedResponseDtos.subList(start, end), pageable, feedResponseDtos.size());
        return new FeedListResponseDto(page);
    }

    private Pageable getPageable(int pageNum) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNum, 15, sort);
    }


    class PartyDateComparator implements Comparator<Party> {
        @Override
        public int compare(Party d1, Party d2) {
            return d1.getPartyDate().compareTo(d2.getPartyDate());
        }
    }

    class CntComparator implements Comparator<Mountain10ResponseDto>{
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

}

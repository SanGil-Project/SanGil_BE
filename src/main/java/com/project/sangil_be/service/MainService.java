package com.project.sangil_be.service;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.Attend;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.Party;
import com.project.sangil_be.repository.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    // 즐겨찾기순위 10개로 수정해야됨
    // 수정 필요
    public List<Mountain10ResponseDto> get10Mountains(UserDetailsImpl userDetails) {
        List<Mountain100> mountain100List = mountain100Repository.findAll();
        List<Mountain10ResponseDto> mountain10ResponseDtos = new ArrayList<>();
        Collections.shuffle(mountain100List);

        int star=0;
        float starAvr=0;
        for (int i = 0; i < 10; i++) {
            boolean bookMark = bookMarkRepository.existsByMountain100IdAndUserId(mountain100List.get(i).getMountain100Id(),userDetails.getUser().getUserId());

            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100Id(mountain100List.get(i).getMountain100Id());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                star+=mountainComments.get(i).getStar();
                starAvr = (float)star/mountainComments.size();
            }
            Mountain10ResponseDto mountain10ResponseDto = new Mountain10ResponseDto(mountain100List.get(i),String.format("%.1f",starAvr),bookMark);
            mountain10ResponseDtos.add(mountain10ResponseDto);
        }
        return mountain10ResponseDtos;
    }


    class PartyDateComparator implements Comparator<Party> {
        @Override
        public int compare(Party d1, Party d2) {
            return d1.getPartyDate().compareTo(d2.getPartyDate());
        }

    }

}

package com.project.sangil_be.etc.utils;


import com.project.sangil_be.feed.dto.FeedResponseDto;
import com.project.sangil_be.feed.repository.FeedCommentRepository;
import com.project.sangil_be.feed.repository.FeedRepository;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.FeedComment;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.Party;
import com.project.sangil_be.mountain.dto.MCommentRequestDto;
import com.project.sangil_be.mountain.repository.MountainCommentRepository;
import com.project.sangil_be.party.dto.PartyListDto;
import com.project.sangil_be.party.dto.PartyRequestDto;
import com.project.sangil_be.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Validator {
    private final FeedRepository feedRepository;
    private final FeedCommentRepository feedCommentRepository;
    private final PartyRepository partyRepository;
    private final MountainCommentRepository mountainCommentRepository;


    public Page<PartyListDto> overPagesParty(List<PartyListDto> partiesList, int start, int end, Pageable pageable, int page) {
        Page<PartyListDto> pages = new PageImpl<>(partiesList.subList(start, end), pageable, partiesList.size());
        if(page > pages.getTotalPages()) {
            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
        }
        return pages;
    }

    public Page<FeedResponseDto> overPagesFeed(List<FeedResponseDto> feedsList, int start, int end, Pageable pageable, int page) {
        Page<FeedResponseDto> pages = new PageImpl<>(feedsList.subList(start, end), pageable, feedsList.size());
        if(page > pages.getTotalPages()) {
            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
        }
        return pages;
    }

    public void blankContent(PartyRequestDto partyRequestDto) {
        if(partyRequestDto.getPartyContent() == null){
            throw new IllegalArgumentException("내용을 입력해 주세요.");
        }
    }

    public void emptyMComment(MCommentRequestDto mCommentRequestDto) {
        if (mCommentRequestDto.getMountainComment() == null) {
            throw new IllegalArgumentException("댓글을 입력하세요");
        }
    }

    public void feedAuthCheck(Long feedId, Long userId) {
        Feed feed = getFeed(feedId);
        if (feed.getUser().getUserId() != userId) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    public void feedCommentAuthCheck(Long feedCommentId, Long userId) {
        FeedComment feedComment = getFeedComment(feedCommentId);
        if (feedComment.getUser().getUserId() != userId) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    public void partyAuthCheck(Long partyId, Long userId) {
        Party party = getParty(partyId);
        if (party.getUser().getUserId() != userId) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    public void mCommentAuthCheck(Long mountainCommentId, Long userId) {
        MountainComment mountainComment = getMountainComment(mountainCommentId, userId);
        if (mountainComment.getUserId() != userId) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    private MountainComment getMountainComment(Long mountainCommentId, Long userId) {
        return mountainCommentRepository.findByMountainCommentId(mountainCommentId);
    }

    private Party getParty(Long partyId) {
        return partyRepository.findByPartyId(partyId);
    }

    private FeedComment getFeedComment(Long feedCommentId) {
        return feedCommentRepository.findByFeedCommentId(feedCommentId);
    }

    private Feed getFeed(Long feedId) {
        return feedRepository.findByFeedId(feedId);
    }


}
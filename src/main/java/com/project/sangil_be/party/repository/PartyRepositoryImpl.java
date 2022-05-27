package com.project.sangil_be.party.repository;

import com.project.sangil_be.party.dto.PartyListDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.sangil_be.model.QParty.party;

@RequiredArgsConstructor
public class PartyRepositoryImpl implements PartyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PartyListDto> searchPage(String keyword, Pageable pageable) {
        QueryResults<PartyListDto> results = queryFactory
                .select(Projections.constructor(PartyListDto.class,
                        party.partyId,
                        party.user.nickname,
                        party.title,
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<PartyListDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}

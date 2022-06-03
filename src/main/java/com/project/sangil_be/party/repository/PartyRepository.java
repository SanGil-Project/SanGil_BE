package com.project.sangil_be.party.repository;

import com.project.sangil_be.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long>,PartyRepositoryCustom {
    List<Party> findAllByOrderByCreatedAtDesc();

    Party findByPartyIdOrderByPartyDateAsc(Long partyId);

    Party findByPartyId(Long partyId);
}

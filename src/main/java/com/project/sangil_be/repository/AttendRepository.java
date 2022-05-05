package com.project.sangil_be.repository;

import com.project.sangil_be.model.Attend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendRepository extends JpaRepository<Attend, Long> {
    Attend findByPartyId(Long partyId);
}

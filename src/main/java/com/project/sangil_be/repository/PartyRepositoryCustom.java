package com.project.sangil_be.repository;

import com.project.sangil_be.dto.PartyListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartyRepositoryCustom {
    Page<PartyListDto> searchPage(String keyword, Pageable pageable);
}

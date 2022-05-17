package com.project.sangil_be.repository;

import com.project.sangil_be.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MountainRepositoryCustom {
    Page<SearchDto> searchPageSimple(String keyword, Pageable pageable);
}

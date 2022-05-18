package com.project.sangil_be.repository;

import com.project.sangil_be.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MountainRepositoryCustom {
    Page<SearchDto> searchPage(String keyword, Pageable pageable);
}

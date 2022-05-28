package com.project.sangil_be.mountain.repository;

import com.project.sangil_be.mainpage.dto.NearbyMountainListDto;
import com.project.sangil_be.mountain.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MountainRepositoryCustom {
    Page<SearchDto> searchPage(String keyword, Pageable pageable);
    Page<NearbyMountainListDto> nearByMountain(double x1,double x2,double x3,double x4, Pageable pageable);
}

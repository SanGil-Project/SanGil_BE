package com.project.sangil_be.mountain.repository;

import com.project.sangil_be.mountain.dto.BookMarkResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookMarkRepositoryCustom {
    Page<BookMarkResponseDto> bookMarkMountain(Long userId, Pageable pageable);
}

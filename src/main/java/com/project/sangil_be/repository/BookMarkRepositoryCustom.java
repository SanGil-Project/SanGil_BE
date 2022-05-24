package com.project.sangil_be.repository;

import com.project.sangil_be.dto.BookMarkResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookMarkRepositoryCustom {
    Page<BookMarkResponseDto> bookMarkMountain(Long userId, Pageable pageable);
}

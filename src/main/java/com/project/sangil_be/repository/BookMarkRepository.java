package com.project.sangil_be.repository;

import com.project.sangil_be.model.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository <BookMark, Long> {

    BookMark findByMountain100IdAndUserId(Long mountainId, Long userId);
}

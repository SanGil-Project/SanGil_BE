package com.project.sangil_be.repository;

import com.project.sangil_be.model.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository <BookMark, Long>,BookMarkRepositoryCustom {

    List<BookMark> findAllByUserId(Long userId);

    BookMark findByMountainIdAndUserId(Long mountainId, Long userId);

    boolean existsByMountainIdAndUserId(Long mountainId, Long userId);

}

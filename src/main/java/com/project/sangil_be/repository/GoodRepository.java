package com.project.sangil_be.repository;

import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {

    Optional<Good> findByFeedIdAndUserId(Long feedId, Long id);

    boolean existsByFeedIdAndUserId(Long feedId, Long user);

    List<Good> findByFeedId(Feed feed);

    void deleteByFeedId(Long feedId);
}
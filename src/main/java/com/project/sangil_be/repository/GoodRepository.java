package com.project.sangil_be.repository;

import com.project.sangil_be.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {

    Optional<Good> findByFeedIdAndUserId(Long feedId, Long id);

    boolean existsByFeedIdAndUserId(Long feedId, Long user);

<<<<<<< HEAD
    void deleteByFeedId(Long feedId);

    Long findByFeedId(Long feedId);
=======
    List<Good> findByFeedId(Long feed);

    void deleteByFeedId(Long feedId);

//    Good existsByFeedIdAndUserId(Long feedId, Long userId);
>>>>>>> de1fe887814e7019225612f2f344d379f813899c
}

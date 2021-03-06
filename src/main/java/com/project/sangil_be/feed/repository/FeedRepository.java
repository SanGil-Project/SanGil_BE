package com.project.sangil_be.feed.repository;

import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByUser(User user);

    List<Feed> findAllByOrderByCreatedAtDesc();

    Long countAllByUser(User user);

    Feed findByFeedId(Long feedId);

    List<Feed> findAllByUserOrderByCreatedAtDesc(User user);
}

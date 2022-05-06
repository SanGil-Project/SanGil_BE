package com.project.sangil_be.repository;

import com.project.sangil_be.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findAllByOrderByCreatedAtDesc();
}

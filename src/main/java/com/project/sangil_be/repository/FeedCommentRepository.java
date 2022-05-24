package com.project.sangil_be.repository;

import com.project.sangil_be.model.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    FeedComment findByFeedCommentId(Long feedCommentId);

    void deleteByFeedCommentId(Long feedCommentId);
}

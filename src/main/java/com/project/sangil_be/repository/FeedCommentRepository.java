package com.project.sangil_be.repository;

import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    FeedComment findByFeedCommentId(Long feedCommentId);

    void deleteByFeedCommentId(Long feedCommentId);


    List<FeedComment> findAllByFeedOrderByCreatedAtDesc(Feed feed);

    Long countAllByFeed(Feed feed);

}

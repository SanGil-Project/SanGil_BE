package com.project.sangil_be.feed.repository;

import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long>,FeedCommentRepositoryCustom {
    FeedComment findByFeedCommentId(Long feedCommentId);

    void deleteByFeedCommentId(Long feedCommentId);


    List<FeedComment> findAllByFeedOrderByCreatedAtDesc(Feed feed);

    Long countAllByFeed(Feed feed);

}

package com.project.sangil_be.model;

import com.project.sangil_be.dto.FeedCommentReqDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FeedComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedCommentId;

    @Column(nullable = false)
    private String mountainComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedid")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    public FeedComment(FeedCommentReqDto feedCommentReqDto, Feed feed, User user) {
        this.mountainComment=feedCommentReqDto.getFeedComment();
        this.feed = feed;
        this.user = user;
    }

    public void update(FeedCommentReqDto feedCommentReqDto, UserDetailsImpl userDetails) {
        this.mountainComment = feedCommentReqDto.getFeedComment();
        this.user = userDetails.getUser();
    }
}

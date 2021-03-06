package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Feed extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(nullable = false)
    private String feedImgUrl;

    @Column(nullable = false)
    private String feedContent;

    @OneToMany(mappedBy = "feed" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<FeedComment> feedComments;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="userid")
    private User user;

    public Feed(String feedContent, String feedImgUrl, User user) {
        this.feedContent = feedContent;
        this.feedImgUrl = feedImgUrl;
        this.user = user;
    }

}

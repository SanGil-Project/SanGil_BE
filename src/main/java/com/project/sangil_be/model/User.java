package com.project.sangil_be.model;

import com.project.sangil_be.dto.UsernameRequestDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.Party;
import com.project.sangil_be.model.UserTitle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userImgUrl;

    @Column(nullable = false)
    private String userTitle;

    @Column(nullable = false)
    private String userTitleImgUrl;

    @Column(nullable = false)
    private String socialId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Party> parties;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Feed> feeds;

    public User(String username, String socialId, String encodedPassword, String nickname, String userImageUrl, String userTitle, String userTitleImgUrl) {
        this.username = username;
        this.socialId = socialId;
        this.password = encodedPassword;
        this.nickname = nickname;
        this.userImgUrl = userImageUrl;
        this.userTitle = userTitle;
        this.userTitleImgUrl=userTitleImgUrl;
    }

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public void editimage(String profileImageUrl) {
        this.userImgUrl = profileImageUrl;
    }

    public void update(UserTitle userTitle) {
        this.userTitle= userTitle.getUserTitle();
        this.userTitleImgUrl = userTitle.getCTitleImgUrl();
    }

    public void editname(UsernameRequestDto usernameRequestDto) {
        this.nickname=usernameRequestDto.getNickname();
    }
}

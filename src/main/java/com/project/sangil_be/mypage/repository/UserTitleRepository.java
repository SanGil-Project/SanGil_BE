package com.project.sangil_be.mypage.repository;

import com.project.sangil_be.model.UserTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTitleRepository extends JpaRepository<UserTitle,Long> {
    UserTitle findByUserTitle(String s);
}

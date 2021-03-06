package com.project.sangil_be.login.repository;

import com.project.sangil_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    User findByUserId(Long userId);

    User findBySocialId(String socialId);

    Optional<User> findByNickname(String sender);
}

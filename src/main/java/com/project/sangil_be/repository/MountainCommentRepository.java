package com.project.sangil_be.repository;

import com.project.sangil_be.model.MountainComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MountainCommentRepository extends JpaRepository<MountainComment, Long> {
    List<MountainComment> findAllByMountain100Id(Long mountain100Id);

    Optional<MountainComment> findByUserId(Long userId);
}
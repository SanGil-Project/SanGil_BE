package com.project.sangil_be.repository;

import com.project.sangil_be.model.MountainComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MountainCommentRepository extends JpaRepository<MountainComment, Long> {
    List<MountainComment> findAllByMountain100Id(Long mountain100Id);
}

package com.project.sangil_be.repository;

import com.project.sangil_be.model.Tracking;
import com.project.sangil_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    List<Tracking> findAllByUser(User user);
}

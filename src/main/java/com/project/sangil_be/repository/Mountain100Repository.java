package com.project.sangil_be.repository;

import com.project.sangil_be.model.Mountain100;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface Mountain100Repository extends JpaRepository<Mountain100, Long> {
    @Query("select u from Mountain100 u where u.mountain like %:keyword% or u.mountainAddress like %:keyword%")
    List<Mountain100> searchAllByByMountain(@Param("keyword") String keyword);
}

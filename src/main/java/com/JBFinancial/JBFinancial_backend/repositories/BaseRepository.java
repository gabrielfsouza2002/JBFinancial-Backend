package com.JBFinancial.JBFinancial_backend.repositories;

import com.JBFinancial.JBFinancial_backend.domain.base.Base;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseRepository extends JpaRepository<Base, Long> {
    List<Base> findByUserId(String userId);
}
package com.skcc.oversea.common.repository;

import com.skcc.oversea.common.entity.Common;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonRepository extends JpaRepository<Common, String> {
    // Add custom query methods as needed
}
package com.skcc.oversea.teller.repository;

import com.skcc.oversea.teller.entity.Teller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TellerRepository extends JpaRepository<Teller, String> {
    // Add custom query methods as needed
}
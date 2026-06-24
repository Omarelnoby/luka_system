package com.luka.system.repository;

import com.luka.system.model.Supplies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SuppliesRepository extends JpaRepository<Supplies, Long> {
    @Query("SELECT COALESCE(MAX(s.id), 0) FROM Supplies s")
    int findMaxId();
}

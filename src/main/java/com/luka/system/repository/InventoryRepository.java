package com.luka.system.repository;

import com.luka.system.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductCodeAndManufacturerId(String productCode, Long manufacturerId);

}


//List<Inventory> findByManufacturerId(Long manufacturerId);
package com.luka.system.repository;

import com.luka.system.model.Supplier;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.ScopedValue;

public interface SupplierRepository
        extends JpaRepository<Supplier, Long> {
}
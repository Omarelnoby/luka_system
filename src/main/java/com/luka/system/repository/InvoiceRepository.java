package com.luka.system.repository;

import com.luka.system.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT COALESCE(MAX(s.id), 0) FROM Invoice s")
    int findMaxId();
}

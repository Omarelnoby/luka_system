package com.luka.system.service;

import com.luka.system.model.Supplier;
import com.luka.system.model.Inventory;
import com.luka.system.model.Invoice;
import com.luka.system.model.InvoiceItem;
import com.luka.system.repository.SupplierRepository;
import com.luka.system.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public Supplier createSupplier(Supplier supplier) {

        Supplier saved =repository.save(supplier);
        System.out.println("Saved ID: " + saved.getSupplierId());
        return saved;
    }

    public List<Supplier> getAllSuppliers() {
        return repository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return repository.getReferenceById(id);
    }

    public void deleteSupplier(Long id) {
        repository.deleteById(id);
    }

    /**************************************/

    @Transactional
    public Supplier updateSupplier(Supplier updatedSupplier, Long id) {

        Supplier existingSupplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));


        /*
         * STEP 3:
         * UPDATE BASIC INFO
         */
        existingSupplier.setSupplierName(updatedSupplier.getSupplierName());
        //existingSupplier.setSupplierId(updatedSupplier.getSupplierId());
        existingSupplier.setAddress(updatedSupplier.getAddress());
        existingSupplier.setMobileNumber(updatedSupplier.getMobileNumber());
        existingSupplier.setTotalAmount(
                updatedSupplier.getTotalAmount()
        );
        existingSupplier.setPaidAmount(
                updatedSupplier.getPaidAmount()
        );
        existingSupplier.setRemainingAmount(existingSupplier.getTotalAmount().subtract(existingSupplier.getPaidAmount()));




        return repository.save(existingSupplier);
    }

}
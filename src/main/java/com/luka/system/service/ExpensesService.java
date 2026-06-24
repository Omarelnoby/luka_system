package com.luka.system.service;

import com.luka.system.model.*;
import com.luka.system.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesService {

    private final ExpensesRepository repository;
    private final SupplierRepository supplierRepository;

    public ExpensesService(ExpensesRepository repository, SupplierRepository supplierRepository) {
        this.repository = repository;
        this.supplierRepository  = supplierRepository;
    }

    /*
     * CREATE
     */
    @Transactional
    public Expenses createExpenses(Expenses money) {
        if(money.getExpenseType().equals("SUPPLIER")) {
            Supplier supplier = supplierRepository.findById(money.getSupplierId()).orElseThrow(() -> new RuntimeException("Supplier not found"));
            supplier.setPaidAmount(supplier.getPaidAmount().add(money.getTotalAmount()));
            supplierRepository.save(supplier);
        }

        return repository.save(money);
    }

    /*
     * UPDATE
     */
    @Transactional
    public Expenses updateExpenses(Expenses updatedMoney, Long id) {

        Expenses existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(" Expenses not found"));

        existing.setExpenseType(updatedMoney.getExpenseType());
        existing.setExpenseDate(updatedMoney.getExpenseDate());
        existing.setAmrAmount(updatedMoney.getAmrAmount());
        existing.setSupplierId(updatedMoney.getSupplierId());
        existing.setTotalAmount(updatedMoney.getTotalAmount());
        existing.setElnobyAmount(updatedMoney.getElnobyAmount());
        existing.setExpenseDetails(updatedMoney.getExpenseDetails());


        return repository.save(existing);
    }

    /*
     * GET ALL
     */
    public List<Expenses> getExpenses() {
        return repository.findAll();
    }

    /*
     * GET BY ID
     */
    public Expenses findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incoming money not found"));
    }

    /*
     * DELETE
     */
    @Transactional
    public void deleteExpenses(Long id) {

        Expenses money = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expenses not found"));
        Supplier supplier = supplierRepository.findById(money.getSupplierId()).orElseThrow(() -> new RuntimeException("Supplier not found"));
        supplier.setPaidAmount(supplier.getPaidAmount().subtract(money.getTotalAmount()));
        supplierRepository.save(supplier);
        repository.delete(money);
    }
}
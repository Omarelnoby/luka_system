package com.luka.system.controller;

import com.luka.system.model.Supplier;
import com.luka.system.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier Supplier) {
        return service.createSupplier(Supplier);
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return service.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable Long id) {
        return service.getSupplierById(id);
    }
    @PutMapping("/{id}")
    public Supplier update(@RequestBody Supplier Supplier, @PathVariable long id) {
        return service.updateSupplier(Supplier,id);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        service.deleteSupplier(id);
    }
}
package com.luka.system.controller;

import com.luka.system.model.Supplies;
import com.luka.system.repository.SuppliesRepository;
import com.luka.system.service.SuppliesService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SuppliesController {

    private final SuppliesService service;
    public SuppliesController(SuppliesService service) {
        this.service = service;
    }

    // =========================================================
    // ✅ CREATE SUPPLY
    // =========================================================
    @PostMapping
    public Supplies create(@RequestBody Supplies request) {
        System.out.println("request for creating supply is : "+request);
        return service.createSupply(request);
    }

    // =========================================================
    // ✅ GET ALL
    // =========================================================
    @GetMapping
    public List<Supplies> getAll() {
        return service.getAllSupplies();
    }

    // =========================================================
    // ✅ GET BY ID
    // =========================================================
    @GetMapping("/next-ref")
    public String getNextReference() {
        Integer maxId = service.getMaxId();
        int next = (maxId == null ? 1 : maxId + 1);
        return String.format("SUP-%04d", next);
    }

    // =========================================================
    // ✅ DELETE
    // =========================================================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSupply(id);
    }

    // =========================================================
    // ✅ UPDATE
    // =========================================================
    @PutMapping("/{id}")
    public Supplies update(@PathVariable Long id,
                           @RequestBody Supplies request) {
        return service.updateSupply(id, request);
    }
}
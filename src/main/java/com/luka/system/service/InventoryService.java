package com.luka.system.service;

import com.luka.system.model.Inventory;
import com.luka.system.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public Inventory save(Inventory inventory) {
        return repository.save(inventory);
    }

    public List<Inventory> findAll() {
        return repository.findAll();
    }

    public Inventory findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Inventory> getByManufacturerId(Long manufacturerId) {
        return repository.findAll().stream()
                .filter(inventory ->
                        inventory.getManufacturerId() != null &&
                                inventory.getManufacturerId().equals(manufacturerId))
                .toList();
    }
}
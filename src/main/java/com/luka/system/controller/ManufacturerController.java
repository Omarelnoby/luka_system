package com.luka.system.controller;

import com.luka.system.model.*;
import com.luka.system.model.Invoice;
import com.luka.system.service.CustomerService;
import com.luka.system.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService service;

    public ManufacturerController(ManufacturerService service) {
        this.service = service;
    }

    @PostMapping
    public Manufacturer createManufacturer(@RequestBody Manufacturer manufacturer) {
        return service.createManufacturer(manufacturer);
    }

    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return service.getAllManufacturers();
    }

    @GetMapping("/{id}")
    public Manufacturer getById(@PathVariable Long id) {
        return service.getManufacturerById(id);
    }
    @PutMapping("/{id}")
    public Manufacturer update(@RequestBody Manufacturer manufacturer, @PathVariable long id) {
        return service.updateManufacturer(manufacturer,id);
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        service.deleteManufacturer(id);
    }
}
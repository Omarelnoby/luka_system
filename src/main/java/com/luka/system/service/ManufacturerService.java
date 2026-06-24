package com.luka.system.service;

import com.luka.system.model.Customer;
import com.luka.system.model.Manufacturer;
import com.luka.system.repository.ManufacturerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    private final ManufacturerRepository repository;

    public ManufacturerService(ManufacturerRepository repository) {
        this.repository = repository;
    }

    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return repository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        return repository.findAll();
    }
    public Manufacturer getManufacturerById(Long id) {
        return repository.getReferenceById(id);
    }

    public void deleteManufacturer(Long id) {
        repository.deleteById(id);
    }

    /**************************************/

    @Transactional
    public Manufacturer updateManufacturer(Manufacturer updatedManufacturer, Long id) {

        Manufacturer existingManufacturer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));


        /*
         * STEP 3:
         * UPDATE BASIC INFO
         */
        existingManufacturer.setManufacturerName(updatedManufacturer.getManufacturerName());
        existingManufacturer.setMobileNumber(updatedManufacturer.getMobileNumber());







        return repository.save(existingManufacturer);
    }
}

package com.luka.system.service;

import com.luka.system.model.Inventory;
import com.luka.system.model.Supplier;
import com.luka.system.model.Supplies;
import com.luka.system.model.Manufacturer;
import com.luka.system.repository.InventoryRepository;
import com.luka.system.repository.SupplierRepository;
import com.luka.system.repository.SuppliesRepository;
import com.luka.system.repository.ManufacturerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class SuppliesService {

    private final SuppliesRepository suppliesRepo;
    private final SupplierRepository supplierRepo;

    private final ManufacturerRepository manufacturerRepo;
    private final InventoryRepository inventoryRepo;

    public SuppliesService(SuppliesRepository suppliesRepo,
                           ManufacturerRepository manufacturerRepo,InventoryRepository inventoryRepo,SupplierRepository supplierRepo) {
        this.suppliesRepo = suppliesRepo;
        this.manufacturerRepo = manufacturerRepo;
        this.inventoryRepo = inventoryRepo;
        this.supplierRepo=supplierRepo;

    }
    // ✅ GET ALL
    // =========================================================
    public List<Supplies> getAllSupplies() {
        return suppliesRepo.findAll();
    }

    // =========================================================
    // ✅ GET BY ID
    // =========================================================
    public Supplies getSupplyById(Long id) {
        return suppliesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply not found"));
    }
    // =========================================================
    // ✅ CREATE SUPPLY
    // =========================================================
    @Transactional
    public Supplies createSupply(Supplies request) {

        Supplier supplier =  supplierRepo.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
//System.out.println("Manufacturer ID: " + manufacturer.getManufacturerId());
        Supplies supply = new Supplies();
        supply.setReferenceNumber(request.getReferenceNumber());
        supply.setSupplyDate(request.getSupplyDate());
        supply.setDescription(request.getDescription());
        supply.setSupplierId(request.getSupplierId());
        supply.setSupplierName(request.getSupplierName());

        BigDecimal totalItems = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        Map<Long, BigDecimal> productMap = new HashMap<>();

        for (Map.Entry<Long, BigDecimal> entry : request.getProducts().entrySet()) {

            Long productId = entry.getKey();
            BigDecimal quantity = entry.getValue();

            Inventory product = inventoryRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // ✅ Update inventory
            product.setAvailableQuantity(
                    product.getAvailableQuantity().add(quantity)
            );
            // ✅ Update inventory
            product.setTotalQuantity(
                    product.getTotalQuantity().add(quantity)
            );
            inventoryRepo.save(product);

            BigDecimal price = product.getBuyingPrice();
            BigDecimal itemTotal = price.multiply(quantity);

            totalItems = totalItems.add(quantity);
            totalPrice = totalPrice.add(itemTotal);

            productMap.put(productId, quantity);
        }
        BigDecimal discount = request.getDiscount();
        BigDecimal net = totalPrice.subtract(
                totalPrice.multiply(discount)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );
        supply.setProducts(productMap);
        supply.setTotalItems(totalItems);
        supply.setOriginalPrice(totalPrice);
        supply.setDiscount(request.getDiscount());
        supply.setTotalPrice(net);

        /*supplier.setRemainingAmount(
                supplier.getRemainingAmount().add(totalPrice)
        );*/
        supplier.setTotalAmount(
                supplier.getTotalAmount().add(net)
        );;
        supplierRepo.save(supplier);
        return suppliesRepo.save(supply);
    }
    /********************************************************************************/
    // =========================================================
    // ✅ UPDATE
    // =========================================================
    @Transactional
    public Supplies updateSupply(Long id, Supplies request) {

        Supplies existing = suppliesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply not found"));


        // 🔵 STEP 1: deleting old data
        deleteSupply(id);

        Supplies New=createSupply(request);


        return suppliesRepo.save(New);
    }

    // =========================================================
    // ✅ DELETE
    // =========================================================
    @Transactional
    public void deleteSupply(Long id) {
        Supplies supply = suppliesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply not found"));
        Supplier supplier = supplierRepo.findById(supply.getSupplierId()).orElseThrow(() -> new RuntimeException("supplier not found"));
        supplier.setTotalAmount(supplier.getTotalAmount().subtract(supply.getTotalPrice()));
       // supplier.setRemainingAmount(supplier.getRemainingAmount().subtract(supply.getTotalPrice()));

        supplierRepo.save(supplier);
        // 🔴 rollback inventory before delete
        if (supply.getProducts() != null) {
            for (Map.Entry<Long, BigDecimal> entry : supply.getProducts().entrySet()) {

                Inventory product = inventoryRepo.findById(entry.getKey())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                product.setAvailableQuantity(
                        product.getAvailableQuantity().subtract(entry.getValue())
                );
                product.setTotalQuantity(
                        product.getTotalQuantity().subtract(entry.getValue())
                );

                inventoryRepo.save(product);
            }
        }

        suppliesRepo.delete(supply);
    }
    /**********************************************************/
    public int getMaxId(){
        return suppliesRepo.findMaxId();
    }
}
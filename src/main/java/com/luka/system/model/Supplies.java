package com.luka.system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "supplies")
public class Supplies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long supplyId;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "date")
    private String date;

    @Column(name = "description")
    private String description;

    @Column(name = "total_items")
    private BigDecimal totalItems;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "supplier_id")
    private long supplierId;

    @Column(name = "supplier_name")
    private String supplierName;



    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();



    @ElementCollection
    @CollectionTable(name = "supply_products", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, BigDecimal> products;

    // ================= GETTERS & SETTERS =================

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long id) {
        this.supplyId = id;
    }

    public String getSupplyDate() {
        return  date;
    }

    public void setSupplyDate(String Date) {
        this.date = Date;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(BigDecimal TotalItems) {
        this.totalItems= TotalItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal TotalPrice) {
        this.totalPrice = TotalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Map<Long, BigDecimal> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, BigDecimal> products) {
        this.products = products;
    }


    public long getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public BigDecimal getOriginalPrice() {

        return originalPrice;
    }
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

}
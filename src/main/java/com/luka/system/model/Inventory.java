package com.luka.system.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "manufacturer_id")
    private Long manufacturerId;

    @Column(name = "buying_price")
    private BigDecimal buyingPrice;

    @Column(name = "selling_price_wholesale")
    private BigDecimal sellingPriceWholesale;

    @Column(name = "selling_price_sectoral")
    private BigDecimal sellingPriceSectoral;

    @Column(name = "selling_price_website")
    private BigDecimal sellingPriceWebsite;

    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    @Column(name = "sold_quantity_wholesale")
    private BigDecimal soldQuantityWholesale;

    @Column(name = "sold_quantity_sectoral")
    private BigDecimal soldQuantitySectoral;

    @Column(name = "sold_quantity_website")
    private BigDecimal soldQuantityWebsite;

    @Column(name = "total_sold_quantity")
    private BigDecimal totalSoldQuantity;

    @Column(name = "available_quantity")
    private BigDecimal availableQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "manufacturer_name_ar")
    private String manufacturerNameAr;

// Getters and Setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getManufacturerNameAr() {
        return manufacturerNameAr;
    }

    public void setManufacturerNameAr(String manufacturerNameAr) {
        this.manufacturerNameAr = manufacturerNameAr;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getSellingPriceWholesale() {
        return sellingPriceWholesale;
    }

    public void setSellingPriceWholesale(BigDecimal sellingPriceWholesale) {
        this.sellingPriceWholesale = sellingPriceWholesale;
    }

    public BigDecimal getSellingPriceSectoral() {
        return sellingPriceSectoral;
    }

    public void setSellingPriceSectoral(BigDecimal sellingPriceSectoral) {
        this.sellingPriceSectoral = sellingPriceSectoral;
    }

    public BigDecimal getSellingPriceWebsite() {
        return sellingPriceWebsite;
    }

    public void setSellingPriceWebsite(BigDecimal sellingPriceWebsite) {
        this.sellingPriceWebsite = sellingPriceWebsite;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getSoldQuantityWholesale() {
        return soldQuantityWholesale;
    }

    public void setSoldQuantityWholesale(BigDecimal soldQuantityWholesale) {
        this.soldQuantityWholesale = soldQuantityWholesale;
    }

    public BigDecimal getSoldQuantitySectoral() {
        return soldQuantitySectoral;
    }

    public void setSoldQuantitySectoral(BigDecimal soldQuantitySectoral) {
        this.soldQuantitySectoral = soldQuantitySectoral;
    }

    public BigDecimal getSoldQuantityWebsite() {
        return soldQuantityWebsite;
    }

    public void setSoldQuantityWebsite(BigDecimal soldQuantityWebsite) {
        this.soldQuantityWebsite = soldQuantityWebsite;
    }

    public BigDecimal getTotalSoldQuantity() {
        return totalSoldQuantity;
    }

    public void setTotalSoldQuantity(BigDecimal totalSoldQuantity) {
        this.totalSoldQuantity = totalSoldQuantity;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }}
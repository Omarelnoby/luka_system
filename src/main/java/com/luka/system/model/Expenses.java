package com.luka.system.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate expenseDate;

    private String expenseDetails;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    private Long supplierId;

    private BigDecimal totalAmount;

    private BigDecimal lukaAmount;

    private BigDecimal elnobyAmount;

    private BigDecimal amrAmount;

    public Expenses() {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }
    public void setExpenseDetails(String expenseDetails) {
        this.expenseDetails = expenseDetails;
    }
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setLukaAmount(BigDecimal lukaAmount) {
        this.lukaAmount = lukaAmount;
    }
    public void setElnobyAmount(BigDecimal elnobyAmount) {
        this.elnobyAmount = elnobyAmount;
    }
    public void setAmrAmount(BigDecimal amrAmount) {
        this.amrAmount = amrAmount;
    }
    public Long getId() {
        return id;
    }
    public LocalDate getExpenseDate() {
        return expenseDate;
    }
    public String getExpenseDetails() {
        return expenseDetails;
    }
    public ExpenseType getExpenseType() {
        return expenseType;
    }
    public Long getSupplierId() {
        return supplierId;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public BigDecimal getLukaAmount() {
        return lukaAmount;
    }
    public BigDecimal getElnobyAmount() {
        return elnobyAmount;
    }
    public BigDecimal getAmrAmount() {
        return amrAmount;
    }

}
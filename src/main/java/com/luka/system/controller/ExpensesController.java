package com.luka.system.controller;

import com.luka.system.model.Expenses;
import com.luka.system.service.ExpensesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    private final ExpensesService service;

    public ExpensesController(ExpensesService service) {
        this.service = service;
    }

    @PostMapping
    public Expenses createExpenses(@RequestBody Expenses expenses) {
        return service.createExpenses(expenses);
    }

    @GetMapping
    public List<Expenses> getAllExpenses() {
        return service.getAllExpenses();
    }

    @GetMapping("/{id}")
    public Expenses getById(@PathVariable Long id) {
        return service.getExpensesById(id);
    }
    @PutMapping("/{id}")
    public Expenses update(@RequestBody Expenses expenses, @PathVariable long id) {
        return service.updateExpenses(expenses,id);
    }

    @DeleteMapping("/{id}")
    public void deleteExpenses(@PathVariable Long id) {
        service.deleteExpenses(id);
    }
}
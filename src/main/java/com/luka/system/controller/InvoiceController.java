package com.luka.system.controller;

import com.luka.system.model.IncomingMoney;
import com.luka.system.model.Invoice;
import com.luka.system.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice ) {
        return service.createInvoice(invoice);
    }

    @GetMapping("/next-ref")
    public String getNextReference() {
        Integer maxId = service.getMaxId();
        int next = (maxId == null ? 1 : maxId + 1);
        return String.format("INV-%04d", next);
    }

    @PutMapping
    public Invoice update(@RequestBody Invoice invoice,@PathVariable long id) {
        return service.updateInvoice(invoice,id);
    }

    @GetMapping
    public List<Invoice> getAll() {
        return service.getInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getById(@PathVariable Long id) {
        return service.findById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteInvoice(id);
    }
}

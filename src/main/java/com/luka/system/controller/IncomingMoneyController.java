package com.luka.system.controller;

import com.luka.system.model.IncomingMoney;
import com.luka.system.service.IncomingMoneyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incoming-money")
@CrossOrigin("*")
public class IncomingMoneyController {

    private final IncomingMoneyService service;

    public IncomingMoneyController(IncomingMoneyService service) {
        this.service = service;
    }

    /*
     * CREATE
     */
    @PostMapping
    public IncomingMoney create(@RequestBody IncomingMoney money) {

        return service.createIncoming(money);
    }

    /*
     * UPDATE
     */
    @PutMapping("/{id}")
    public IncomingMoney update(
            @RequestBody IncomingMoney money,
            @PathVariable Long id
    ) {

        return service.updateIncoming(money, id);
    }

    /*
     * GET ALL
     */
    @GetMapping
    public List<IncomingMoney> getAll() {

        return service.getIncomings();
    }

    /*
     * GET BY ID
     */
    @GetMapping("/{id}")
    public IncomingMoney getById(@PathVariable Long id) {

        return service.findById(id);
    }

    /*
     * DELETE
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {

        service.deleteIncoming(id);
    }
}
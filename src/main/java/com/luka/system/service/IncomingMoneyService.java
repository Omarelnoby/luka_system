package com.luka.system.service;

import com.luka.system.model.Customer;
import com.luka.system.model.IncomingMoney;
import com.luka.system.repository.CustomerRepository;
import com.luka.system.repository.IncomingMoneyRepository;
import com.luka.system.repository.ManufacturerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingMoneyService {

    private final IncomingMoneyRepository repository;
    private final CustomerRepository customerRepository;
    private final ManufacturerRepository manufacturerRepository;

    public IncomingMoneyService(IncomingMoneyRepository repository, CustomerRepository customerRepository, ManufacturerRepository manufacturerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    /*
     * CREATE
     */
    @Transactional
    public IncomingMoney createIncoming(IncomingMoney money) {

        Customer customer = customerRepository.findById(money.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setPaidAmount(customer.getPaidAmount().add(money.getNetAmount()));
        customerRepository.save(customer);
        return repository.save(money);
    }

    /*
     * UPDATE
     */
    @Transactional
    public IncomingMoney updateIncoming(IncomingMoney updatedMoney, Long id) {

        IncomingMoney existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incoming money not found"));

        existing.setCustomerId(updatedMoney.getCustomerId());
        existing.setAmount(updatedMoney.getAmount());
        existing.setNetAmount(updatedMoney.getNetAmount());
        existing.setPaymentDate(updatedMoney.getPaymentDate());
        existing.setPaymentType(updatedMoney.getPaymentType());
        existing.setNotes(updatedMoney.getNotes());

        return repository.save(existing);
    }

    /*
     * GET ALL
     */
    public List<IncomingMoney> getIncomings() {
        return repository.findAll();
    }

    /*
     * GET BY ID
     */
    public IncomingMoney findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incoming money not found"));
    }

    /*
     * DELETE
     */
    @Transactional
    public void deleteIncoming(Long id) {

        IncomingMoney money = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incoming money not found"));
        Customer customer = customerRepository.findById(money.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setPaidAmount(customer.getPaidAmount().subtract(money.getNetAmount()));
        customerRepository.save(customer);
        repository.delete(money);
    }
}
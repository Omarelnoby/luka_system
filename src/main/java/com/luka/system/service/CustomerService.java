package com.luka.system.service;

import com.luka.system.model.Customer;
import com.luka.system.model.Inventory;
import com.luka.system.model.Invoice;
import com.luka.system.model.InvoiceItem;
import com.luka.system.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer createCustomer(Customer customer) {

                Customer saved =repository.save(customer);
        System.out.println("Saved ID: " + saved.getCustomerId());
        return saved;
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return repository.getReferenceById(id);
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }

    /**************************************/

    @Transactional
    public Customer updateCustomer(Customer updatedCustomer, Long id) {

        Customer existingCustomer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        /*
         * STEP 3:
         * UPDATE BASIC INFO
         */
        existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
        existingCustomer.setCustomerType(updatedCustomer.getCustomerType());
        //existingCustomer.setCustomerId(updatedCustomer.getCustomerId());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber());
        existingCustomer.setTotalPurchasingAmount(
                updatedCustomer.getTotalPurchasingAmount()
        );
        existingCustomer.setPaidAmount(
                updatedCustomer.getPaidAmount()
        );




        return repository.save(existingCustomer);
    }

}
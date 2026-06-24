package com.luka.system.service;

import com.luka.system.model.*;
import com.luka.system.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository repo;
    private final InventoryRepository inventoryRepo; // ✅ add this
    private final CustomerRepository customerRepo; // ✅ add this
    public InvoiceService(InvoiceRepository repo, InventoryRepository inventoryRepo, CustomerRepository customerRepo) {
        this.repo = repo;
        this.inventoryRepo = inventoryRepo;
        this.customerRepo = customerRepo;
    }

    public Invoice save(Invoice invoice) {

        BigDecimal total = BigDecimal.ZERO;

        if (invoice.getItems() != null) {
            for (InvoiceItem item : invoice.getItems()) {

                item.setInvoice(invoice);

                BigDecimal itemTotal =
                        item.getPrice().multiply(item.getQuantity());

                item.setTotal(itemTotal);

                total = total.add(itemTotal);
            }
        }

        invoice.setTotalAmount(total);

        BigDecimal discount = invoice.getDiscount() != null
                ? invoice.getDiscount()
                : BigDecimal.ZERO;

        BigDecimal net = total.subtract(total.multiply(discount).divide(BigDecimal.valueOf(100)));

        invoice.setNetAmount(net);

        return repo.save(invoice);
    }

    /******************************************/

    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal available = BigDecimal.ZERO;

        if (invoice.getItems() != null) {
            for (InvoiceItem item : invoice.getItems()) {

                // item.setInvoice(invoice);

                // ✅ GET PRODUCT
                Inventory product = inventoryRepo
                        .findByProductCodeAndManufacturerId(
                                item.getProductCode(),
                                item.getManufacturerId()
                        )
                        .orElseThrow(() -> new RuntimeException(
                                "Product not found: code=" + item.getProductCode() +
                                        ", manufacturerId=" + item.getManufacturerId()
                        ));
                available = product.getAvailableQuantity();
                if (available == null) {
                    throw new RuntimeException("Available quantity is NULL for product " + product.getProductCode());
                }
                // ✅ CHECK STOCK
                if (item.getQuantity().compareTo(available) > 0) {
                    throw new RuntimeException(
                            "Not enough stock for " + product.getProductCode()
                    );
                }


                String type = String.valueOf(invoice.getInvoiceType());
                if (type.trim().equals("قطاعى")) {
                    product.setSoldQuantitySectoral(
                            product.getSoldQuantitySectoral().add(item.getQuantity())

                    );

                } else if (type.trim().equals("جملة")) {
                    product.setSoldQuantityWholesale(
                            product.getSoldQuantityWholesale().add(item.getQuantity())

                    );

                }
                else if (type.trim().equals("ويبسايت")) {
                    product.setSoldQuantityWebsite(
                            product.getSoldQuantityWebsite().add(item.getQuantity())

                    );

                }

                inventoryRepo.save(stockManagement(product));

                // ✅ CALCULATE ITEM TOTAL
                BigDecimal itemTotal =
                        item.getPrice().multiply(item.getQuantity()).multiply(
                                BigDecimal.ONE.subtract(
                                        item.getDiscount() != null
                                                ? item.getDiscount().divide(BigDecimal.valueOf(100))
                                                : BigDecimal.ZERO
                                )
                        );;

                item.setTotal(itemTotal);
                total = total.add(itemTotal);
            }
        }

        invoice.setTotalAmount(total);

        BigDecimal discount = invoice.getDiscount() != null
                ? invoice.getDiscount()
                : BigDecimal.ZERO;

        BigDecimal net = total.subtract(
                total.multiply(discount).divide(BigDecimal.valueOf(100))
        );

        invoice.setNetAmount(net);
        Customer customer = customerRepo.findById(invoice.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setTotalPurchasingAmount(customer.getTotalPurchasingAmount().add(net));
        customerRepo.save(customer);
        return repo.save(invoice);
    }

    /************************************/
    public Invoice findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Invoice> getInvoices() {
        return repo.findAll();
    }
/****************************************************************************************/
    @Transactional
    public void deleteInvoice(Long id) {

        Invoice invoice = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getItems() != null) {

            for (InvoiceItem item : invoice.getItems()) {

                Inventory product = inventoryRepo
                        .findByProductCodeAndManufacturerId(
                                item.getProductCode(),
                                item.getManufacturerId()
                        )
                        .orElseThrow(() -> new RuntimeException(
                                "Product not found: " + item.getProductCode()
                        ));

                String type = String.valueOf(invoice.getInvoiceType()).trim();

                // ✅ RETURN STOCK
                if (type.equals("جملة")) {

                    product.setSoldQuantityWholesale(
                            product.getSoldQuantityWholesale()
                                    .subtract(item.getQuantity())
                    );

                } else if (type.equals("قطاعى")) {

                    product.setSoldQuantitySectoral(
                            product.getSoldQuantitySectoral()
                                    .subtract(item.getQuantity())
                    );

                } else if (type.equals("ويبسايت")) {

                    product.setSoldQuantityWebsite(
                            product.getSoldQuantityWebsite()
                                    .subtract(item.getQuantity())
                    );
                }

                // ✅ RECALCULATE STOCK
                inventoryRepo.save(stockManagement(product));
            }
        }
            Customer customer = customerRepo.findById(invoice.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setTotalPurchasingAmount(customer.getTotalPurchasingAmount().subtract(invoice.getNetAmount()));
        customerRepo.save(customer);
        // ✅ DELETE INVOICE
        repo.delete(invoice);
    }
    /**************************************/

    @Transactional
    public Invoice updateInvoice(Invoice updatedInvoice, Long id) {

        Invoice existingInvoice = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        /*
         * STEP 1:
         * RETURN OLD STOCK
         */
        if (existingInvoice.getItems() != null) {

            for (InvoiceItem oldItem : existingInvoice.getItems()) {

                Inventory product = inventoryRepo
                        .findByProductCodeAndManufacturerId(
                                oldItem.getProductCode(),
                                oldItem.getManufacturerId()
                        )
                        .orElseThrow(() -> new RuntimeException(
                                "Product not found: " + oldItem.getProductCode()
                        ));

                String oldType = String.valueOf(existingInvoice.getInvoiceType());

                if (oldType.equals("جملة")) {

                    product.setSoldQuantityWholesale(
                            product.getSoldQuantityWholesale()
                                    .subtract(oldItem.getQuantity())
                    );

                } else if (oldType.equals("قطاعى")) {

                    product.setSoldQuantitySectoral(
                            product.getSoldQuantitySectoral()
                                    .subtract(oldItem.getQuantity())
                    );

                } else if (oldType.equals("ويبسايت")) {

                    product.setSoldQuantityWebsite(
                            product.getSoldQuantityWebsite()
                                    .subtract(oldItem.getQuantity())
                    );
                }

                inventoryRepo.save(stockManagement(product));
            }
        }

        /*
         * STEP 2:
         * REMOVE OLD ITEMS
         */
        existingInvoice.getItems().clear();

        /*
         * STEP 3:
         * UPDATE BASIC INFO
         */
        existingInvoice.setCustomerName(updatedInvoice.getCustomerName());
        existingInvoice.setPhone(updatedInvoice.getPhone());
        existingInvoice.setCustomerAddress(updatedInvoice.getCustomerAddress());
        existingInvoice.setInvoiceType(updatedInvoice.getInvoiceType());
        existingInvoice.setDiscount(updatedInvoice.getDiscount());
        existingInvoice.setInvoiceReference(
                updatedInvoice.getInvoiceReference()
        );
        existingInvoice.setOrderDate(updatedInvoice.getOrderDate());

        /*
         * STEP 4:
         * ADD NEW ITEMS + DEDUCT STOCK
         */
        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceItem newItem : updatedInvoice.getItems()) {

            Inventory product = inventoryRepo
                    .findByProductCodeAndManufacturerId(
                            newItem.getProductCode(),
                            newItem.getManufacturerId()
                    )
                    .orElseThrow(() -> new RuntimeException(
                            "Product not found: " + newItem.getProductCode()
                    ));

            BigDecimal available = product.getAvailableQuantity();

            if (newItem.getQuantity().compareTo(available) > 0) {
                throw new RuntimeException(
                        "Not enough stock for " + product.getProductCode()
                );
            }

            String newType = String.valueOf(updatedInvoice.getInvoiceType());

            if (newType.equals("جملة")) {

                product.setSoldQuantityWholesale(
                        product.getSoldQuantityWholesale()
                                .add(newItem.getQuantity())
                );

            } else if (newType.equals("قطاعى")) {

                product.setSoldQuantitySectoral(
                        product.getSoldQuantitySectoral()
                                .add(newItem.getQuantity())
                );

            } else if (newType.equals("ويبسايت")) {

                product.setSoldQuantityWebsite(
                        product.getSoldQuantityWebsite()
                                .add(newItem.getQuantity())
                );
            }

            inventoryRepo.save(stockManagement(product));

            /*
             * CALCULATE ITEM TOTAL
             */
            BigDecimal itemTotal =
                    newItem.getPrice()
                            .multiply(newItem.getQuantity())
                            .multiply(
                                    BigDecimal.ONE.subtract(
                                            newItem.getDiscount() != null
                                                    ? newItem.getDiscount()
                                                      .divide(BigDecimal.valueOf(100))
                                                    : BigDecimal.ZERO
                                    )
                            );

            newItem.setTotal(itemTotal);

            total = total.add(itemTotal);

            /*
             * IMPORTANT
             */
            newItem.setInvoice(existingInvoice);

            existingInvoice.getItems().add(newItem);
        }

        /*
         * STEP 5:
         * RECALCULATE TOTALS
         */
        existingInvoice.setTotalAmount(total);

        BigDecimal discount =
                existingInvoice.getDiscount() != null
                        ? existingInvoice.getDiscount()
                        : BigDecimal.ZERO;

        BigDecimal net = total.subtract(
                total.multiply(discount)
                        .divide(BigDecimal.valueOf(100))
        );

        existingInvoice.setNetAmount(net);

        return repo.save(existingInvoice);
    }

    /***********************************************************************************/
    private Inventory stockManagement(Inventory product) {

        BigDecimal soldWholesale = product.getSoldQuantityWholesale();
        BigDecimal soldSectoral = product.getSoldQuantitySectoral();
        BigDecimal soldWebsite = product.getSoldQuantityWebsite();
        BigDecimal total = product.getTotalQuantity();
        BigDecimal totalSold = soldWholesale.add(soldSectoral).add(soldWebsite);
        product.setTotalSoldQuantity(
                totalSold
        );

        // ✅ UPDATE INVENTORY
        product.setAvailableQuantity(
                total.subtract(totalSold)
        );
        return product;
    }
    /**********************************************************/
    public int getMaxId(){
        return repo.findMaxId();
    }
}

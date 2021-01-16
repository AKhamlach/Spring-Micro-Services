package org.sid.billingservice.controllers;

import org.sid.billingservice.Feign.CustomerRestClient;
import org.sid.billingservice.Feign.ProductItemRestClient;
import org.sid.billingservice.entitites.Bill;
import org.sid.billingservice.repositories.BillRepository;
import org.sid.billingservice.repositories.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {

    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerServiceClient;
    private final ProductItemRestClient inventoryServiceClient;

    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerServiceClient, ProductItemRestClient inventoryServiceClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerServiceClient = customerServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GetMapping("/bills/full/{id}")
    Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill=billRepository.findById(id).get();
        bill.setCustomer(customerServiceClient.getCustomerById(bill.getCustomerID()));
        bill.setProductItems(productItemRepository.findByBillId(id));
        bill.getProductItems().forEach(pi ->{
            pi.setProduct(inventoryServiceClient.getProductById(pi.getProductID()));
        });
        return bill;
    }
}

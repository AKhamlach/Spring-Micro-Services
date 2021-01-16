package org.sid.billingservice;

import org.sid.billingservice.Feign.CustomerRestClient;
import org.sid.billingservice.Feign.ProductItemRestClient;
import org.sid.billingservice.entitites.Bill;
import org.sid.billingservice.entitites.Customer;
import org.sid.billingservice.entitites.Product;
import org.sid.billingservice.entitites.ProductItem;
import org.sid.billingservice.repositories.BillRepository;
import org.sid.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerServiceClient,
                            ProductItemRestClient inventoryServiceClient){

        return args -> {
            Customer customer=customerServiceClient.getCustomerById(1L);
            Bill bill1=billRepository.save(new Bill(null,new Date(),null,null,customer.getId()));
            PagedModel<Product> productPagedModel=inventoryServiceClient.PageProducts();
            productPagedModel.forEach(p->{
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+new Random().nextInt(100));
//                productItem.setPhoto(p.getPhoto());
                productItem.setProductID(p.getId());
                productItem.setBill(bill1);
                productItemRepository.save(productItem);
            });
        };
    }
}

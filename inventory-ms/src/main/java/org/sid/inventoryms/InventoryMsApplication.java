package org.sid.inventoryms;

import org.sid.inventoryms.entitites.Product;
import org.sid.inventoryms.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryMsApplication.class, args);
    }


    @Bean
    CommandLineRunner start(ProductRepository productRepository,
                            RepositoryRestConfiguration repositoryRestConfiguration) {
        repositoryRestConfiguration.exposeIdsFor(Product.class);
        return args -> {
            productRepository.save(new Product(null, "Ordinateur", 788, 12));
            productRepository.save(new Product(null, "Imprimante", 788, 12));
            productRepository.save(new Product(null, "Smartphone", 788, 12));
            productRepository.findAll().forEach(p -> {
                System.out.println(p.getName());
            });
        };
    }
}

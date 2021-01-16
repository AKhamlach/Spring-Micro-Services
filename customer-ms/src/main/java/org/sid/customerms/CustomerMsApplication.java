package org.sid.customerms;

import org.sid.customerms.entities.Customer;
import org.sid.customerms.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerMsApplication {



    public static void main(String[] args) {
        SpringApplication.run(CustomerMsApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            RepositoryRestConfiguration repositoryRestConfiguration){
        repositoryRestConfiguration.exposeIdsFor(Customer.class);
        return args -> {
            customerRepository.save(new Customer(null,"Mohammed","med@gmail.com"));
            customerRepository.save(new Customer(null,"Hassan","hassan@gmail.com"));
            customerRepository.save(new Customer(null,"Salma","salima@gmail.com"));
            customerRepository.findAll().forEach(c->{
                System.out.println(c.toString());
            });
        };
    }
}

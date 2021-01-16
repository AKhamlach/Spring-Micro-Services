package org.sid.billingservice.Feign;


import org.sid.billingservice.entitites.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="PRODUCT-SERVICE")
public interface ProductItemRestClient {

    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long id);

    @GetMapping("/products")
    PagedModel<Product> PageProducts();
}

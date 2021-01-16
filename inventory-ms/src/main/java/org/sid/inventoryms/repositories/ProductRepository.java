package org.sid.inventoryms.repositories;

import org.sid.inventoryms.entitites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product,Long> {
}

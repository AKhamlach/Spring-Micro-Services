package org.sid.secservice.sec.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sid.secservice.sec.entities.AppUser;

@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}

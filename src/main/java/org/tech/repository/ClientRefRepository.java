package org.tech.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.tech.entity.ClientRef;

@ApplicationScoped
public class ClientRefRepository implements PanacheRepository<ClientRef> {

    public ClientRef findById(Long id) {
        return find("id", id).firstResult(); // or you can use Panache's built-in findById(id) method
    }

    public boolean existsById(Long id) {
        return findByIdOptional(id).isPresent();
    }

    // Method to check if entity exists by id and date
    public boolean existsByIdAndDate(Long id, String date) {
        return find("id = ?1 and date = ?2", id, date).firstResultOptional().isPresent();
    }

}

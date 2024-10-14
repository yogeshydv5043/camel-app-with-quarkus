package org.tech.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.tech.entity.PersonModel;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<PersonModel> {
}

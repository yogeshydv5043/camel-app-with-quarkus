package org.tech.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tech.entity.PersonModel;
import org.tech.repository.PersonRepository;
import org.tech.xmlModel.Person;

import java.util.List;

@ApplicationScoped
@Named("personService")
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Inject
    private PersonRepository personRepository;

    @Transactional
    public void addPerson(Person personRequest) {
        PersonModel person = new PersonModel();
        person.setUsername(personRequest.getUsername());
        person.setPassword(personRequest.getPassword());
        person.setEmail(personRequest.getEmail());
        person.setPhone(personRequest.getPhone());
        personRepository.persist(person);
        System.out.println("Person Saved in DB : " + person.toString());
    }

    public List<PersonModel> getAllPerson() {
        return personRepository.findAll().list();
    }
}

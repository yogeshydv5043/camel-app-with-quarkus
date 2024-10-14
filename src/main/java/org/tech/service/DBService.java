package org.tech.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tech.dto.request.ClientRefDto;
import org.tech.entity.ClientRef;
import org.tech.repository.ClientRefRepository;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class DBService {

    @Inject
    private ClientRefRepository clientRefRepository;

    //add
    @Transactional
    public String addClientRef(ClientRefDto clientRefDto) {
        if (clientRefDto.getName() == null) {
            throw new RuntimeException("name cannot be null");
        }
        ClientRef clientRef = new ClientRef();
        clientRef.setName(clientRefDto.getName());
        clientRef.setDate(LocalDate.now().toString());
        clientRefRepository.persist(clientRef);
        return "Record added successfully with name " + clientRefDto.getName();
    }

    //get All
    public List<ClientRef> getall() {
       List<ClientRef> list = clientRefRepository.findAll().list();
        return list;
    }


}

package com.gilles.gestionDeStock.controller;

import com.gilles.gestionDeStock.controller.api.ClientApi;
import com.gilles.gestionDeStock.dto.ClientDto;
import com.gilles.gestionDeStock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer idClient) {
        return clientService.findById(idClient);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void delete(Integer idClient) {
        clientService.delete(idClient);

    }
}

package com.gilles.gestionDeStock.services;

import com.gilles.gestionDeStock.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientDto dto);

    ClientDto findById(Integer id);

    List<ClientDto> findAll();

    void delete(Integer id);
}

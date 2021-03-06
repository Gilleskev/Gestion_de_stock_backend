package com.gilles.gestionDeStock.repository;


import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {

    Optional<CommandeClient> findCommandeClientByCode(String code);



}

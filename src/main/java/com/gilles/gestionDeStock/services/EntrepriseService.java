package com.gilles.gestionDeStock.services;

import com.gilles.gestionDeStock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto dto);

    EntrepriseDto findById(Integer id);

    EntrepriseDto findBySiret(String siret);

    List<EntrepriseDto> findAll();

    void delete(Integer id);
}

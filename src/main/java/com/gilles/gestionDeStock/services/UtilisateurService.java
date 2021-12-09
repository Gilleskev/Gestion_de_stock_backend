package com.gilles.gestionDeStock.services;

import com.gilles.gestionDeStock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    UtilisateurDto findByEmail(String email);

    List<UtilisateurDto> findAll();

    void delete(Integer id);
}

package com.gilles.gestionDeStock.repository;


import com.gilles.gestionDeStock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    Optional<Entreprise> findEntrepriseBySiret(String siret);
}

package com.gilles.gestionDeStock.repository;

import com.gilles.gestionDeStock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {
}

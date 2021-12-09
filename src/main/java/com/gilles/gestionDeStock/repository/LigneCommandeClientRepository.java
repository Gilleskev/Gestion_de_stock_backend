package com.gilles.gestionDeStock.repository;


import com.gilles.gestionDeStock.model.LigneCommandeClent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClent, Integer> {
}

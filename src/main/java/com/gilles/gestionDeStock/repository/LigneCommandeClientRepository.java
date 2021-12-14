package com.gilles.gestionDeStock.repository;


import com.gilles.gestionDeStock.model.CommandeClient;
import com.gilles.gestionDeStock.model.LigneCommandeClent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClent, Integer> {

    List<LigneCommandeClent> findAllByCommandeClientId(Integer id);
}

package com.gilles.gestionDeStock.repository;

import com.gilles.gestionDeStock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {

    List<LigneVente> findAllByArticleId(Integer idArticle);
}

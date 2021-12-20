package com.gilles.gestionDeStock.services;

import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.gilles.gestionDeStock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);

    List<LigneCommandeFournisseurDto> finHistoriqueCommandeFournisseur(Integer idArticle);

    List<ArticleDto> findAllArticleByIdCategorie(Integer idCategorie);

    void delete(Integer id);

}

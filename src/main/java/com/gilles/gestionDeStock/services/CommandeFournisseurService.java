package com.gilles.gestionDeStock.services;

import com.gilles.gestionDeStock.dto.*;
import com.gilles.gestionDeStock.dto.CommandeFournisseurDto;
import com.gilles.gestionDeStock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande,  Integer idArticle);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByCode(String code);

    List<LigneCommandeFournisseurDto> findAllLigneCommandeFournisseurByIdCommandeFournisseur(Integer idCommande);

    List<CommandeFournisseurDto> findAll();

    void delete(Integer id);

    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

}

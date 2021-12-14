package com.gilles.gestionDeStock.services;

import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

    CommandeClientDto save(CommandeClientDto dto);

    CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeClientDto updateClient(Integer idCommande, Integer idClient);

    CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande,  Integer idArticle);

    // suppréssion de la ligne de commande client
    CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeClientDto findById(Integer id);

    CommandeClientDto findByCode(String code);

    List<LigneCommandeClientDto> findAllLigneCommandeClientByIdCommandeClient(Integer idCommande);

    List<CommandeClientDto> findAll();

    void delete(Integer id);

}

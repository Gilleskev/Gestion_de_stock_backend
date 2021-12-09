package com.gilles.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gilles.gestionDeStock.model.Article;
import com.gilles.gestionDeStock.model.CommandeClient;
import com.gilles.gestionDeStock.model.LigneCommandeClent;
import com.gilles.gestionDeStock.model.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeClientDto {

    private Integer id;

    private ArticleDto article;

    @JsonIgnore
    private CommandeClientDto commandeClient;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise;


    public static LigneCommandeClientDto fromEntity(LigneCommandeClent ligneCommandeClient){
        if (ligneCommandeClient == null){
            return null;
        }
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .idEntreprise(ligneCommandeClient.getIdEntreprise())
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .build();
    }

    public static LigneCommandeClent toEntity(LigneCommandeClientDto ligneCommandeClientDto){
        if (ligneCommandeClientDto == null){
            return null;
        }
        LigneCommandeClent ligneCommandeClent = new LigneCommandeClent();
        ligneCommandeClent.setId(ligneCommandeClientDto.getId());
        ligneCommandeClent.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClent.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClent.setIdEntreprise(ligneCommandeClientDto.getIdEntreprise());
        ligneCommandeClent.setArticle(ligneCommandeClent.getArticle());
       // ligneCommandeClent.setCommandeClient(ligneCommandeClent.getCommandeClient());
        return ligneCommandeClent;
    }
}

package com.gilles.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gilles.gestionDeStock.model.LigneCommandeClient;
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


    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient){
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

    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto){
        if (ligneCommandeClientDto == null){
            return null;
        }
        LigneCommandeClient ligneCommandeClent = new LigneCommandeClient();
        ligneCommandeClent.setId(ligneCommandeClientDto.getId());
        ligneCommandeClent.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClent.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClent.setIdEntreprise(ligneCommandeClientDto.getIdEntreprise());
        ligneCommandeClent.setArticle(ligneCommandeClent.getArticle());
       // ligneCommandeClent.setCommandeClient(ligneCommandeClent.getCommandeClient());
        return ligneCommandeClent;
    }
}

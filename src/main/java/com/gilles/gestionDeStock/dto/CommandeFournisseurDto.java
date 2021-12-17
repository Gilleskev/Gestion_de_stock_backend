package com.gilles.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gilles.gestionDeStock.model.*;
import lombok.Builder;
import lombok.Data;


import javax.persistence.Column;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeFournisseurDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private Integer idEntreprise;

    private FournisseurDto fournisseur;

    @JsonIgnore
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur){
        if (commandeFournisseur == null) {
            return  null;
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .etatCommande(commandeFournisseur.getEtatCommande())
                //.fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                /*.ligneCommandeFournisseurs(
                        commandeFournisseur.getLigneCommandeFournisseurs() != null ?
                                commandeFournisseur.getLigneCommandeFournisseurs().stream()
                                        .map(LigneCommandeFournisseurDto :: fromEntity)
                                        .collect(Collectors.toList()) : null

                )*/
                .build();
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto){
      if (commandeFournisseurDto == null){
          return null;
      }
      CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
      commandeFournisseur.setId(commandeFournisseurDto.getId());
      commandeFournisseur.setCode(commandeFournisseurDto.getCode());
      commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
      commandeFournisseur.setIdEntreprise(commandeFournisseurDto.getIdEntreprise());
      commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());
     // commandeFournisseur.setFournisseur(commandeFournisseur.getFournisseur());
     // commandeFournisseur.setLigneCommandeFournisseurs(commandeFournisseur.getLigneCommandeFournisseurs());
        return commandeFournisseur;
    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}

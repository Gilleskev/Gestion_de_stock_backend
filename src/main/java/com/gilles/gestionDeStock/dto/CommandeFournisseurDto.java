package com.gilles.gestionDeStock.dto;

import com.gilles.gestionDeStock.model.CommandeClient;
import com.gilles.gestionDeStock.model.CommandeFournisseur;
import com.gilles.gestionDeStock.model.Fournisseur;
import com.gilles.gestionDeStock.model.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeFournisseurDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private Integer idEntreprise;

    private FournisseurDto fournisseur;

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
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
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
      commandeFournisseur.setFournisseur(commandeFournisseur.getFournisseur());
     // commandeFournisseur.setLigneCommandeFournisseurs(commandeFournisseur.getLigneCommandeFournisseurs());
        return commandeFournisseur;
    }


}

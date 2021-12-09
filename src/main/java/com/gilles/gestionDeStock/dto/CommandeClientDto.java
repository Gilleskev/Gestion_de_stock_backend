package com.gilles.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gilles.gestionDeStock.model.Category;
import com.gilles.gestionDeStock.model.Client;
import com.gilles.gestionDeStock.model.CommandeClient;
import com.gilles.gestionDeStock.model.LigneCommandeClent;
import lombok.Builder;
import lombok.Data;

;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeClientDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private ClientDto client;

    private Integer idEntreprise;

    private List<LigneCommandeClientDto> ligneCommandeClents;

    public static CommandeClientDto fromEntity(CommandeClient commandeClient){

        if (commandeClient == null){
            return null;
            // TODO throw an exception
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .idEntreprise(commandeClient.getIdEntreprise())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
               /* .ligneCommandeClents(
                        commandeClient.getLigneCommandeClents() != null ?
                                commandeClient.getLigneCommandeClents().stream()
                                        .map(LigneCommandeClientDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )*/
                .build();
    }

    public static CommandeClient toEntity(CommandeClientDto commandeClientDto){
        if (commandeClientDto == null){
            return null;
        }
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setClient(commandeClient.getClient());
       // commandeClient.setLigneCommandeClents(commandeClient.getLigneCommandeClents());

        return commandeClient;
    }
}
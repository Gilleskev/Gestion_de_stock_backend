package com.gilles.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gilles.gestionDeStock.model.Adresse;
import com.gilles.gestionDeStock.model.Client;
import com.gilles.gestionDeStock.model.CommandeClient;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ClientDto {

    private Integer id;

    private String nom;

    private String prenom;

    private AdresseDto adresse;

    private String photo;

    private String email;

    private String numTel;

    private Integer idEntreprise;

    private List<CommandeClientDto> commandeClients;

    public static   ClientDto fromEntity(Client client){

        if (client == null){
            return  null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .photo(client.getPhoto())
                .email(client.getEmail())
                .numTel(client.getNumTel())
                .idEntreprise(client.getIdEntreprise())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .commandeClients(
                        client.getCommandeClients() != null ?
                              client.getCommandeClients().stream()
                                      .map(CommandeClientDto::fromEntity)
                                      .collect(Collectors.toList()) : null
                )
                .build();

    }

    public static   Client toEntity(ClientDto clientDto){
        if(clientDto == null){
            return  null;
        }
       Client client = new Client();
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setPhoto(clientDto.getPhoto());
        client.setEmail(clientDto.getEmail());
        client.setNumTel(client.getNumTel());
        client.setIdEntreprise(clientDto.getIdEntreprise());
        client.setAdresse(client.getAdresse());
        client.setCommandeClients(client.getCommandeClients());
        return client;
    }


}

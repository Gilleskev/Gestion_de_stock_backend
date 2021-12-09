package com.gilles.gestionDeStock.dto;

import com.gilles.gestionDeStock.model.Adresse;
import com.gilles.gestionDeStock.model.CommandeFournisseur;
import com.gilles.gestionDeStock.model.Entreprise;
import com.gilles.gestionDeStock.model.Fournisseur;
import lombok.Builder;
import lombok.Data;


import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FournisseurDto {

    private Integer id;

    private String nom;


    private String prenom;

    private AdresseDto adresse;

    private String photo;

    private String email;

    private String numTel;

    private Integer idEntreprise;

    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromEntity(Fournisseur fournisseur){
        if (fournisseur == null){
            return null;
        }
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .photo(fournisseur.getPhoto())
                .email(fournisseur.getEmail())
                .numTel(fournisseur.getNumTel())
                .idEntreprise(fournisseur.getIdEntreprise())
                .commandeFournisseurs(
                        fournisseur.getCommandeFournisseurs() != null ?
                        fournisseur.getCommandeFournisseurs().stream()
                                .map(CommandeFournisseurDto :: fromEntity)
                                .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if (fournisseurDto == null){
            return null;
        }
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setAdresse(fournisseur.getAdresse());
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setEmail(fournisseurDto.getEmail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setIdEntreprise(fournisseurDto.getIdEntreprise());
        fournisseur.setCommandeFournisseurs(fournisseur.getCommandeFournisseurs());
        return fournisseur;
    }
}

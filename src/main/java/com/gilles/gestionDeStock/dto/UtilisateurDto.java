package com.gilles.gestionDeStock.dto;

import com.gilles.gestionDeStock.model.Adresse;
import com.gilles.gestionDeStock.model.Entreprise;
import com.gilles.gestionDeStock.model.Roles;
import com.gilles.gestionDeStock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UtilisateurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private Instant dateDeNaissance;

    private String motDePasse;

    private AdresseDto adresse;

    private String photo;

    private String email;

    private String numTel;

    private EntrepriseDto entreprise;

    private List<RolesDto> roles;


    public static UtilisateurDto fromEntity(Utilisateur utilisateur){
        if (utilisateur == null){
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .motDePasse(utilisateur.getMotDePasse())
               .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .email(utilisateur.getEmail())
                .numTel(utilisateur.getNumTel())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RolesDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){
        if (utilisateurDto == null){
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setNumTel(utilisateurDto.getNumTel());
        utilisateur.setAdresse(utilisateur.getAdresse());
        utilisateur.setEntreprise(utilisateur.getEntreprise());
        utilisateur.setRoles(utilisateur.getRoles());
        return utilisateur;
    }

}

package com.gilles.gestionDeStock.dto;

import com.gilles.gestionDeStock.model.Adresse;
import com.gilles.gestionDeStock.model.Entreprise;
import com.gilles.gestionDeStock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;


import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class EntrepriseDto {

    private Integer id;

    private String nom;

    private String description;

    private AdresseDto adresse;

    private String siret;

    private String codeFisscal;

    private  String photo;

    private String email;

    private String numTel;

    private  String siteWeb;

    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise){
        if (entreprise == null){
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .siret(entreprise.getSiret())
                .codeFisscal(entreprise.getCodeFisscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .siteWeb(entreprise.getSiteWeb())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                /*.utilisateurs(
                        entreprise.getUtilisateurs() != null ?
                                entreprise.getUtilisateurs().stream()
                                        .map(EntrepriseDto :: fromEntity)
                                        .collect(Collectors.toList()) null
                )*/
                .build();
    }

    public static Entreprise toEntity(EntrepriseDto entrepriseDto){
        if (entrepriseDto == null){
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setSiret(entreprise.getSiret());
        entreprise.setCodeFisscal(entreprise.getCodeFisscal());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setSiteWeb(entrepriseDto.getSiteWeb());
        entreprise.setAdresse(entreprise.getAdresse());
        entreprise.setUtilisateurs(entreprise.getUtilisateurs());

        return entreprise;
    }
}

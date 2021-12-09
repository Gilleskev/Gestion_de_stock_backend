package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public  static List<String> validate (UtilisateurDto utilisateurDto){
       List<String> errors = new ArrayList<>();

       if (utilisateurDto == null){
           errors.add("veuillez renseigner votre nom ");
           errors.add("veuillez renseigner votre nom ");
           errors.add("veuillez renseigner votre mot de passe");
           errors.add("veuillez renseigner votre Email ");
           errors.add("veuillez renseigner la date de naissance");

           errors.add("veuillez renseigner l'adresse de l'utilisateur ");

           return errors;
       }

       if (!StringUtils.hasLength(utilisateurDto.getNom())){
           errors.add("veuillez renseigner votre nom ");
       }
        if (!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("veuillez renseigner votre prenom ");
        }
        if (!StringUtils.hasLength(utilisateurDto.getMotDePasse())){
            errors.add("veuillez renseigner votre mot de passe");
        }

        if (!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("veuillez renseigner votre Email ");
        }
        if (utilisateurDto.getDateDeNaissance() == null){
           errors.add("veuillez renseigner la date de naissance");
        }
        if (utilisateurDto.getAdresse()== null){
            errors.add("veuillez renseigner l'adresse de l'utilisateur ");
        }
        else {
            if (!StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())){
                errors.add("Le champ adresse n'est pas renseigné");
            }
            if (!StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostale())){
                errors.add("Le code postale n'est pas renseigné");
            }

            if (!StringUtils.hasLength(utilisateurDto.getAdresse().getVille())){
                errors.add("La ville n'est pas renseigné");
            }
            if (!StringUtils.hasLength(utilisateurDto.getAdresse().getPays())){
                errors.add("Le pays n'est pas renseigné");
            }
        }

       return  errors;

    }
}

package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public  static List<String> validate (EntrepriseDto entrepriseDto){
        List<String> errors = new ArrayList<>();

        if (entrepriseDto == null){
            errors.add("veuillez renseigner le nom de l'entreprise ");
            errors.add("veuillez renseigner la description de l'entreprise ");
            errors.add("veuillez renseigner le Siret de l'entreprise ");
            errors.add("veuillez renseigner l'email de l'entreprise ");
            errors.add("veuillez renseigner le numéro de télephone de l'entreprise ");
            errors.add("veuillez renseigner le site web de l'entreprise ");

            return errors;
        }

        if (!StringUtils.hasLength(entrepriseDto.getNom())){
            errors.add("veuillez renseigner le nom de l'entreprise ");
        }

        if (!StringUtils.hasLength(entrepriseDto.getDescription())){
            errors.add("veuillez renseigner la description de l'entreprise ");
        }

        if (!StringUtils.hasLength(entrepriseDto.getSiret())){
            errors.add("veuillez renseigner le Siret de l'entreprise ");
        }
        if (!StringUtils.hasLength(entrepriseDto.getEmail())){
            errors.add("veuillez renseigner l'email de l'entreprise ");
        }
        if (!StringUtils.hasLength(entrepriseDto.getNumTel())){
            errors.add("veuillez renseigner le numéro de télephone de l'entreprise ");
        }
        if (!StringUtils.hasLength(entrepriseDto.getSiteWeb())){
            errors.add("veuillez renseigner le site web de l'entreprise ");
        }
        if (entrepriseDto.getAdresse() == null){
            errors.add("veuillez renseigner l'adresse de l'entreprise ");
        }
        else {
            if (!StringUtils.hasLength(entrepriseDto.getAdresse().getAdresse1())){
                errors.add("Le champ adresse n'est pas renseigné");
            }
            if (!StringUtils.hasLength(entrepriseDto.getAdresse().getCodePostale())){
                errors.add("Le code postale n'est pas renseigné");
            }

            if (!StringUtils.hasLength(entrepriseDto.getAdresse().getVille())){
                errors.add("La ville n'est pas renseigné");
            }
            if (!StringUtils.hasLength(entrepriseDto.getAdresse().getPays())){
                errors.add("Le pays n'est pas renseigné");
            }
        }

        return  errors;

    }
}

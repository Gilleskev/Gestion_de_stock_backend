package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public  static List<String> validate (FournisseurDto fournisseurDto){
        List<String> errors = new ArrayList<>();

        if (fournisseurDto == null){
            errors.add("veuillez renseigner votre nom ");
            errors.add("veuillez renseigner votre prenom ");;
            errors.add("veuillez renseigner votre Email ");
            errors.add("veuillez renseigner votre numéro de téléphone ");

            return errors;
        }

        if (!StringUtils.hasLength(fournisseurDto.getNom())){
            errors.add("veuillez renseigner votre nom ");
        }
        if (!StringUtils.hasLength(fournisseurDto.getPrenom())){
            errors.add("veuillez renseigner votre prenom ");
        }


        if (!StringUtils.hasLength(fournisseurDto.getEmail())){
            errors.add("veuillez renseigner votre Email ");

        } if (!StringUtils.hasLength(fournisseurDto.getNumTel())){
            errors.add("veuillez renseigner votre numéro de téléphone ");
        }


        return  errors;

    }
}

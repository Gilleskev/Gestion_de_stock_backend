package com.gilles.gestionDeStock.validator;


import com.gilles.gestionDeStock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {

    public static List<String> validate(CommandeFournisseurDto commandeFournisseurDto){

        List<String> errors = new ArrayList<>();

        if (commandeFournisseurDto == null){
            errors.add("veuillez renseigner le code ");
            errors.add("veuillez renseigner la date de la commande ");
            errors.add("veuillez renseigner le fournisseur ");
            return errors;
        }

        if (!StringUtils.hasLength(commandeFournisseurDto.getCode())){
            errors.add("veuillez renseigner le code ");
        }
        if (commandeFournisseurDto.getDateCommande() == null){
            errors.add("veuillez renseigner la date de la commande ");
        }
        if (commandeFournisseurDto.getFournisseur() == null){
            errors.add("veuillez renseigner le fournisseur ");
        }

        return errors;
    }
}

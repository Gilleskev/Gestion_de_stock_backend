package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDto commandeClientDto){

        List<String> errors = new ArrayList<>();

        if (commandeClientDto == null){
            errors.add("veuillez renseigner le code ");
            errors.add("veuillez renseigner la date de la commande ");
            errors.add("veuillez renseigner le client ");
            return errors;
        }

        if (!StringUtils.hasLength(commandeClientDto.getCode())){
            errors.add("veuillez renseigner le code ");
        }
        if (commandeClientDto.getDateCommande() == null){
            errors.add("veuillez renseigner la date de la commande ");
        }
        if (commandeClientDto.getClient() == null){
            errors.add("veuillez renseigner le client ");
        }

        return errors;
    }
}

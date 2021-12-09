package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.CommandeFournisseurDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeClientValidator {

    public static List<String> validate(LigneCommandeClientDto ligneCommandeClientDto){

        List<String> errors = new ArrayList<>();

        if (ligneCommandeClientDto == null){
            errors.add("veuillez renseigner la commande client");
            errors.add("veuillez renseigner le prix Uintaire ");
            errors.add("veuillez renseigner la quantité");
            errors.add("veuillez renseigner l'article");
            return errors;
        }


        if (ligneCommandeClientDto.getCommandeClient() == null){
            errors.add("veuillez renseigner la commande client");
        }
        if (ligneCommandeClientDto.getPrixUnitaire() == null){
            errors.add("veuillez renseigner le prix Uintaire ");
        }
        if (ligneCommandeClientDto.getQuantite() == null){
            errors.add("veuillez renseigner la quantité");
        }
        if (ligneCommandeClientDto.getArticle() == null){
            errors.add("veuillez renseigner l'article");
        }

        return errors;
    }
}

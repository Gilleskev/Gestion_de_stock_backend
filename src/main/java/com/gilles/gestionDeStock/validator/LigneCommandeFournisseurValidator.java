package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeFournisseurDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeFournisseurValidator {
    public static List<String> validate(LigneCommandeFournisseurDto ligneCommandeFournisseurDto){

        List<String> errors = new ArrayList<>();

        if (ligneCommandeFournisseurDto == null){
            errors.add("veuillez renseigner la commande fournisseur");
            errors.add("veuillez renseigner le prix Uintaire ");
            errors.add("veuillez renseigner la quantité");
            errors.add("veuillez renseigner l'article");
            return errors;
        }


        if (ligneCommandeFournisseurDto.getCommandeFournisseur() == null){
            errors.add("veuillez renseigner la commande fournisseur");
        }
        if (ligneCommandeFournisseurDto.getPrixUnitaire() == null){
            errors.add("veuillez renseigner le prix Uintaire ");
        }
        if (ligneCommandeFournisseurDto.getQuantite() == null){
            errors.add("veuillez renseigner la quantité");
        }
        if (ligneCommandeFournisseurDto.getArticle() == null){
            errors.add("veuillez renseigner l'article");
        }

        return errors;
    }
}

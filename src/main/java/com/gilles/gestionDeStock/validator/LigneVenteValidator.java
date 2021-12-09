package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.LigneVenteDto;
import com.gilles.gestionDeStock.dto.RolesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {

    public static List<String> validate(LigneVenteDto ligneVenteDto){

        List<String> errors = new ArrayList<>();

        if (ligneVenteDto == null){
            errors.add("veuillez renseigner la Quantité");
            errors.add("veuillez renseigner le prix unitaire");
            return errors;
        }


        if (ligneVenteDto.getQuantite() == null){
            errors.add("veuillez renseigner la Quantité");
        }
        if (ligneVenteDto.getPrixUnitaire() == null){
            errors.add("veuillez renseigner le prix unitaire");
        }


        return errors;
    }
}

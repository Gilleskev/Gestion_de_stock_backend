package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.gilles.gestionDeStock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenteValidator {

    public static List<String> validate(VentesDto ventesDto){

        List<String> errors = new ArrayList<>();

        if (ventesDto == null){
            errors.add("veuillez renseigner la date de la vente");
            errors.add("veuillez renseigner le code de vente");
            return errors;
        }


        if (ventesDto.getDateVente() == null){
            errors.add("veuillez renseigner la date de la vente");
        }
        if (!StringUtils.hasLength(ventesDto.getCode())){
            errors.add("veuillez renseigner le code de vente");
        }


        return errors;
    }
}

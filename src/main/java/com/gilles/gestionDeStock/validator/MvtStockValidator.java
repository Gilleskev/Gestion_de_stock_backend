package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.MvtStockDto;
import com.gilles.gestionDeStock.dto.RolesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MvtStockValidator {
    public static List<String> validate(MvtStockDto mvtStockDto){

        List<String> errors = new ArrayList<>();

        if (mvtStockDto == null){
            errors.add("veuillez renseigner la date du mouvement");
            errors.add("veuillez renseigner la quantité");
            errors.add("veuillez renseigner le type de mouvement");
            return errors;
        }


        if (mvtStockDto.getDateMvt() == null){
            errors.add("veuillez renseigner la date du mouvement");
        }
        if (mvtStockDto.getQuantite() == null){
            errors.add("veuillez renseigner la quantité");
        }
        if (mvtStockDto.getTypeMvt() == null){
            errors.add("veuillez renseigner le type de mouvement");
        }





        return errors;
    }
}

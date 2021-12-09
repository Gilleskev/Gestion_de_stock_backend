package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.RolesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RolesValidator {

    public static List<String> validate(RolesDto rolesDto){

        List<String> errors = new ArrayList<>();

        if (rolesDto == null){
            errors.add("veuillez renseigner l'utilisateur");
            errors.add("veuillez renseigner le role");
            return errors;
        }


        if (rolesDto.getUtilisateur() == null){
            errors.add("veuillez renseigner l'utilisateur");
        }
        if (!StringUtils.hasLength(rolesDto.getRoleName())){
            errors.add("veuillez renseigner le role");
        }


        return errors;
    }
}

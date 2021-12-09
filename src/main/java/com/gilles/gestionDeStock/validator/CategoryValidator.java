package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {

    public static List<String> validate(CategoryDto categoryDto){

        List<String> errors = new ArrayList<>();
        if (categoryDto == null || !StringUtils.hasLength(categoryDto.getCode())){
            errors.add("veuillez renseignez le code de la categorie");
        }
        return errors;
    }
}

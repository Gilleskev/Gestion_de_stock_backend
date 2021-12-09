package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public  static List<String> validate (ArticleDto articleDto){

        List<String> errors = new ArrayList<>();

        if(articleDto == null){
            errors.add("Veuillez renseigner le code l'article");
            errors.add("Veuillez renseigner la designation l'article");
            errors.add("Veuillez renseigner la category de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
            errors.add("Veuillez renseigner le taux de TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire HT  de l'article");

            return  errors;
        }
        if (!StringUtils.hasLength(articleDto.getCodeArticle())){
            errors.add("Veuillez renseigner le code l'article");
        }
        if (!StringUtils.hasLength(articleDto.getDesignation())){
            errors.add("Veuillez renseigner la designation l'article");
        }
        if (articleDto.getCategory() == null){
            errors.add("Veuillez renseigner la category de l'article");
        }
        if (articleDto.getPrixUntaireTtc() == null){
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        }
        if (articleDto.getTauxTva() == null){
            errors.add("Veuillez renseigner le taux de TVA de l'article");
        }
        if (articleDto.getPrixUnitaireHt() == null){
            errors.add("Veuillez renseigner le prix unitaire HT  de l'article");
        }

        return errors;
    }
}

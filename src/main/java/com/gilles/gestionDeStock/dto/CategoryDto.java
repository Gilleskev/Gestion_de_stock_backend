package com.gilles.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gilles.gestionDeStock.model.Article;
import com.gilles.gestionDeStock.model.Category;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryDto {

    private Integer id;

    private String code;

    private String designation;

    private Integer idEntreprise;

    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category){

        if (category == null){
            return null;
            // TODO throw an exception
        }
        // permet de faire le mapping de category => categoryDto
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .idEntreprise(category.getIdEntreprise())
                .designation(category.getDesignation())
                .articles(
                        category.getArticles() != null ?
                                category.getArticles().stream()
                                .map(ArticleDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto){
        if (categoryDto == null){
            return null;
            // TODO throw an exception
        }
        // permet de faire le mapping de categoryDto => category
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setIdEntreprise(categoryDto.getIdEntreprise());
        category.setDesignation(categoryDto.getDesignation());
        category.setArticles(category.getArticles());
        return category;
    }

}


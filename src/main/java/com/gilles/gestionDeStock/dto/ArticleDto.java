package com.gilles.gestionDeStock.dto;


import com.gilles.gestionDeStock.model.Article;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Builder
@Data
public class ArticleDto {

    private Integer id;

    private String codeArticle;


    private String designation;


    private BigDecimal prixUnitaireHt;


    private BigDecimal tauxTva;


    private BigDecimal prixUntaireTtc;


    private String photo;

    private Integer idEntreprise;

    private CategoryDto category;

   public static  ArticleDto fromEntity(Article article){
       if (article == null){
           return  null;
       }

       return ArticleDto.builder()
               .id(article.getId())
               .codeArticle(article.getCodeArticle())
               .designation(article.getDesignation())
               .prixUnitaireHt(article.getPrixUnitaireHt())
               .tauxTva(article.getTauxTva())
               .prixUntaireTtc(article.getPrixUntaireTtc())
               .photo(article.getPhoto())
               .idEntreprise(article.getIdEntreprise())
               .category(CategoryDto.fromEntity(article.getCategory()))
               .build();
   }

   public static Article toEntity(ArticleDto articleDto){
       if (articleDto == null){
           return null;
       }
       Article article = new Article();
       article.setId(articleDto.getId());
       article.setCodeArticle(articleDto.getCodeArticle());
       article.setDesignation(articleDto.getDesignation());
       article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
       article.setPrixUntaireTtc(articleDto.getPrixUntaireTtc());
       article.setTauxTva(articleDto.getTauxTva());
       article.setPhoto(articleDto.getPhoto());
       article.setIdEntreprise(articleDto.getIdEntreprise());
       article.setCategory(article.getCategory());
       return article;

   }

}




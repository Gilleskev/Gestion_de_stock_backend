package com.gilles.gestionDeStock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidOperationException;
import com.gilles.gestionDeStock.model.Article;
import com.gilles.gestionDeStock.services.ArticleService;
import com.gilles.gestionDeStock.services.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto>{

    private PhotoService photoService;

    private ArticleService articleService;

    public SaveArticlePhoto(PhotoService photoService, ArticleService articleService) {
        this.photoService = photoService;
        this.articleService = articleService;
    }

    @Override
    public ArticleDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ArticleDto article = articleService.findById(id);
        String urlPhoto = photoService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        article.setPhoto(urlPhoto);
        return articleService.save(article);
    }
}

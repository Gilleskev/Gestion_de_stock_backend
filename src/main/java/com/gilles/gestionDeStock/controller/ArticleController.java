package com.gilles.gestionDeStock.controller;

import com.gilles.gestionDeStock.controller.api.ArticleApi;
import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.gilles.gestionDeStock.dto.LigneVenteDto;
import com.gilles.gestionDeStock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer idArticle) {
        return articleService.findById(idArticle);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return articleService.findHistoriqueVentes(idArticle);
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return articleService.findHistoriqueCommandeClient(idArticle);
    }

    @Override
    public List<LigneCommandeFournisseurDto> finHistoriqueCommandeFournisseur(Integer idArticle) {
        return articleService.finHistoriqueCommandeFournisseur(idArticle);
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategorie(Integer idCategorie) {
        return articleService.findAllArticleByIdCategorie(idCategorie);
    }

    @Override
    public void delete(Integer idArticle) {
    articleService.delete(idArticle);
    }
}

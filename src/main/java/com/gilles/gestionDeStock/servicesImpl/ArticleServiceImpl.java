package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.gilles.gestionDeStock.dto.LigneVenteDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Article;
import com.gilles.gestionDeStock.repository.ArticleRepository;
import com.gilles.gestionDeStock.repository.LigneCommandeClientRepository;
import com.gilles.gestionDeStock.repository.LigneCommandeFournisseurRepository;
import com.gilles.gestionDeStock.repository.LigneVenteRepository;
import com.gilles.gestionDeStock.services.ArticleService;
import com.gilles.gestionDeStock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    private LigneVenteRepository venteRepository;

    private LigneCommandeFournisseurRepository commandeFournisseurRepository;

    private LigneCommandeClientRepository commandeClientRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, LigneVenteRepository venteRepository
            , LigneCommandeFournisseurRepository commandeFournisseurRepository
            , LigneCommandeClientRepository commandeClientRepository) {
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.commandeClientRepository = commandeClientRepository;
    }





    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String > errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("L'article n'est pas valide", dto);
            throw  new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(dto))
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        if(id == null){
            log.error("L'ID de l'article est null");
            return  null;
        }

        Optional<Article> article = articleRepository.findById(id);

        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec l'ID = " + id + "n'a ??t?? trouv?? dans la DB", ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)){
            log.error("Le code de l'article est null");
            return  null;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);
        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(()->
            new EntityNotFoundException(
                    "Aucun article avec code = " + codeArticle + " n'a ??t?? trouv?? dans la DB", ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public List<ArticleDto> findAll() {

        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return venteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> finHistoriqueCommandeFournisseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategorie(Integer idCategorie) {
        return articleRepository.findAllByCategoryId(idCategorie).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de l'article est null");
            return;
        }
        articleRepository.deleteById(id);
    }
}

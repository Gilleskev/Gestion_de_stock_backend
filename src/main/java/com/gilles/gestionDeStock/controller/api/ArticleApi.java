package com.gilles.gestionDeStock.controller.api;

import com.gilles.gestionDeStock.dto.ArticleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/articles")
public interface ArticleApi {

    @PostMapping(value = APP_ROOT+ "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer un article (Ajouter / Modifier)", notes = "Cette methode permet d'enregistrer ou de modifier un article",response = ArticleDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet article crée / modifie"),
            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })
    ArticleDto save(@RequestBody ArticleDto dto);


    @GetMapping(value = APP_ROOT + "/articles/{idArticle}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher un article par ID", notes = "Cette methode permet de rechercher un article avec son ID",response = ArticleDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BD avec l'ID fourni")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);


    @GetMapping(value = APP_ROOT + "/articles/{codeArticle}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher un article par code", notes = "Cette methode permet de rechercher un article avec son Code",response = ArticleDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BD avec le code fourni")
    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);



    @GetMapping(value = APP_ROOT + "/articles/all",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Renvoie la liste des articles", notes = "Cette methode permet de chercher et de Renvoie la liste des articles dans BDD",responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des articles / une liste vide")
    })
    List<ArticleDto> findAll();



    @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
    @ApiOperation(value ="Supprimer un article", notes = "Cette methode permet de supprimer un article avec son ID",response = ArticleDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été supprimé")
    })
    void delete(@PathVariable("idArticle") Integer id);
}

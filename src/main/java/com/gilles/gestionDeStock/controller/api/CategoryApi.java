package com.gilles.gestionDeStock.controller.api;


import com.gilles.gestionDeStock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/categories")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT+ "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer une category (Ajouter / Modifier)", notes = "Cette methode permet d'enregistrer ou de modifier une category",response = CategoryDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet category crée / modifie"),
            @ApiResponse(code = 400, message = "L'objet category n'est pas valide")
    })
    CategoryDto save(@RequestBody CategoryDto dto);



    @GetMapping(value = APP_ROOT + "/categories/{idCategory}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher une category par ID", notes = "Cette methode permet de rechercher une category avec son ID",response = CategoryDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La category a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune category n'existe dans la BD avec l'ID fourni")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer id);



    @GetMapping(value = APP_ROOT + "/categories/{codeCategory}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher une category par code", notes = "Cette methode permet de rechercher une category avec son Code",response = CategoryDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La category a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune category n'existe dans la BD avec le code fourni")
    })
    CategoryDto findByCode(@PathVariable("codeCategory") String code);



    @GetMapping(value = APP_ROOT + "/categories/all",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Renvoie la liste des categories", notes = "Cette methode permet de chercher et de Renvoie la liste des categories dans BDD",responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des categories / une liste vide")
    })
    List<CategoryDto> findAll();




    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
    @ApiOperation(value ="Supprimer une category", notes = "Cette methode permet de supprimer une category avec son ID",response = CategoryDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La category a été supprimé")
    })
    void delete(@PathVariable("idCategory") Integer id);

}

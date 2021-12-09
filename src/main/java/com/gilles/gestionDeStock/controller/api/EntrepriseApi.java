package com.gilles.gestionDeStock.controller.api;


import com.gilles.gestionDeStock.dto.CategoryDto;
import com.gilles.gestionDeStock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/entreprise")
public interface EntrepriseApi {

    @PostMapping(value = APP_ROOT+ "/entreprise/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer une entreprise (Ajouter / Modifier)", notes = "Cette methode permet d'enregistrer ou de modifier une entreprise",response = EntrepriseDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet entreprise crée / modifie"),
            @ApiResponse(code = 400, message = "L'objet entreprise n'est pas valide")
    })
    EntrepriseDto save(@RequestBody EntrepriseDto dto);



    @GetMapping(value = APP_ROOT + "/entreprise/{idEntreprise}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher une entreprise par ID", notes = "Cette methode permet de rechercher une entreprise avec son ID",response = EntrepriseDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'entreprise a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune entreprise n'existe dans la BD avec l'ID fourni")
    })
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);




    @GetMapping(value = APP_ROOT + "/entreprise/all",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Renvoie la liste des entreprises", notes = "Cette methode permet de chercher et de Renvoie la liste des entreprises dans BDD",responseContainer = "List<EntrepriseDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des entreprises / une liste vide")
    })
    List<EntrepriseDto> findAll();




    @DeleteMapping(value = APP_ROOT + "/entreprise/delete/{idEntreprise}")
    @ApiOperation(value ="Supprimer une entreprise", notes = "Cette methode permet de supprimer une entreprise avec son ID",response = EntrepriseDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'entreprise a été supprimé")
    })
    void delete(@PathVariable("idEntreprise") Integer id);
}

package com.gilles.gestionDeStock.controller.api;

import com.gilles.gestionDeStock.dto.CategoryDto;
import com.gilles.gestionDeStock.dto.ClientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/clients")
public interface ClientApi {

    @PostMapping(value = APP_ROOT+ "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer un client (Ajouter / Modifier)", notes = "Cette methode permet d'enregistrer ou de modifier un client",response = ClientDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet client crée / modifie"),
            @ApiResponse(code = 400, message = "L'objet client n'est pas valide")
    })
    ClientDto save(@RequestBody ClientDto dto);



    @GetMapping(value = APP_ROOT + "/clients/{idClient}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher un client par ID", notes = "Cette methode permet de rechercher un client avec son ID",response = ClientDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun client n'existe dans la BD avec l'ID fourni")
    })
    ClientDto findById(@PathVariable("idClient") Integer id);




    @GetMapping(value = APP_ROOT + "/clients/all",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher un Client par code", notes = "Cette methode permet de rechercher un Client avec son Code",response = ClientDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Client a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun Client n'existe dans la BD avec le code fourni")
    })
    List<ClientDto> findAll();



    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    @ApiOperation(value ="Supprimer un Client", notes = "Cette methode permet de supprimer un Client avec son ID",response = ClientDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Client a été supprimé")
    })
    void delete(@PathVariable("idClient") Integer id);

}

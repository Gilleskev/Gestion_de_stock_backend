package com.gilles.gestionDeStock.controller.api;


import com.gilles.gestionDeStock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/user")
public interface UtilisateurApi {

    @PostMapping(value = APP_ROOT+ "/user/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer un utilisateur (Ajouter / Modifier)", notes = "Cette methode permet d'enregistrer ou de modifier un utilisateur",response = UtilisateurDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet utilisateur crée / modifie"),
            @ApiResponse(code = 400, message = "L'objet utilisateur n'est pas valide")
    })
    UtilisateurDto save(@RequestBody UtilisateurDto dto);




    @GetMapping(value = APP_ROOT + "/user/{idUtilisateur}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher un utilisateur par ID", notes = "Cette methode permet de rechercher un utilisateur avec son ID",response = UtilisateurDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'existe dans la BD avec l'ID fourni")
    })
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);


   /* @GetMapping(value = APP_ROOT + "/user/{emailUtilisateur}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher un utilisateur par ID", notes = "Cette methode permet de rechercher un utilisateur avec son ID",response = UtilisateurDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'existe dans la BD avec l'ID fourni")
    })
    UtilisateurDto findByEmail(@PathVariable("emailUtilisateur") String email);*/



    @GetMapping(value = APP_ROOT + "/user/all",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Renvoie la liste des utilisateurs", notes = "Cette methode permet de chercher et de Renvoie la liste des Utilisateurs dans BDD",responseContainer = "List<UtilisateurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des utilisateurs / une liste vide")
    })
    List<UtilisateurDto> findAll();



    @DeleteMapping(value = APP_ROOT + "/user/delete/{idUtilisateur}")
    @ApiOperation(value ="Supprimer un utilisateur", notes = "Cette methode permet de supprimer un utilisateur avec son ID",response = UtilisateurDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été supprimé")
    })
    void delete(@PathVariable("idUtilisateur") Integer id);
}

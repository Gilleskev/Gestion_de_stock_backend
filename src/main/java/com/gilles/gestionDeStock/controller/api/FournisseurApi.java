package com.gilles.gestionDeStock.controller.api;


import com.gilles.gestionDeStock.dto.FournisseurDto;
import com.gilles.gestionDeStock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/fournisseur")
public interface FournisseurApi {

    @PostMapping(value = APP_ROOT+ "/fournisseur/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer un fournisseur (Ajouter / Modifier)", notes = "Cette methode permet d'enregistrer ou de modifier un fournisseur",response = FournisseurDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet fournisseur crée / modifie"),
            @ApiResponse(code = 400, message = "L'objet fournisseur n'est pas valide")
    })
    FournisseurDto save(@RequestBody FournisseurDto dto);



    @GetMapping(value = APP_ROOT + "/fournisseur/{idFournisseur}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Rechercher un fournisseur par ID", notes = "Cette methode permet de rechercher un fournisseur avec son ID",response = FournisseurDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun fournisseur n'existe dans la BD avec l'ID fourni")
    })
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);



    @GetMapping(value = APP_ROOT + "/fournisseur/all",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Renvoie la liste des fournisseurs", notes = "Cette methode permet de chercher et de Renvoie la liste des fournisseurs dans BDD",responseContainer = "List<FournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des fournisseurs / une liste vide")
    })
    List<FournisseurDto> findAll();



    @DeleteMapping(value = APP_ROOT + "/fournisseur/delete/{idFournisseur}")
    @ApiOperation(value ="Supprimer un fournisseur", notes = "Cette methode permet de supprimer un fournisseur avec son ID",response = UtilisateurDto.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a été supprimé")
    })
    void delete(@PathVariable("idFournisseur") Integer id);

}

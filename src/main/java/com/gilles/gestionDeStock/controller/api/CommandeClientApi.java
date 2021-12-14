package com.gilles.gestionDeStock.controller.api;

import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+ "/commandeclients")
public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "/commandeclients/create")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @PatchMapping(APP_ROOT + "/commandeclients/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande,
                                                         @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commandeclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande")Integer idCommande
            ,@PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/commandeclients/update/client/{idCommande}/{idClient}")
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande,@PathVariable("idClient")  Integer idClient);

    @PatchMapping(APP_ROOT + "/commandeclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande")Integer idCommande
            ,@PathVariable("idLigneCommande") Integer idLigneCommande,@PathVariable("idArticle")  Integer idArticle);

    @DeleteMapping(APP_ROOT + "/commandeclients/delete/article/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeClientDto>  deleteArticle(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande") Integer idLigneCommande);


    @GetMapping(APP_ROOT +  "/commandeclients/{idCommandeclient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeclient);

    @GetMapping(APP_ROOT +  "/commandeclient/{codeCommandeclient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeclient")  String code);

    @GetMapping(APP_ROOT +  "/commandeclients/all")
    ResponseEntity<List<CommandeClientDto>> findAll();

    @GetMapping(APP_ROOT +  "/commandeclients/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeClientDto>> findAllLigneCommandeClientByIdCommandeClient(@PathVariable("idCommande") Integer idCommande);


    @DeleteMapping(APP_ROOT + "/commandeclients/delete/{idCommandeclient}")
    ResponseEntity<Void> delete(@PathVariable("idCommandeclient") Integer id);
}

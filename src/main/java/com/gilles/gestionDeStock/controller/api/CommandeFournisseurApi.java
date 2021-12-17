package com.gilles.gestionDeStock.controller.api;


import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;
import static com.gilles.gestionDeStock.utils.Constants.FOURNISSEURENDPOINT;

import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.dto.CommandeFournisseurDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.gilles.gestionDeStock.model.EtatCommande;
import com.gilles.gestionDeStock.model.LigneCommandeFournisseur;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(FOURNISSEURENDPOINT)
public interface CommandeFournisseurApi {

    @PostMapping(FOURNISSEURENDPOINT +"/create")
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    @PatchMapping(FOURNISSEURENDPOINT + "/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande,
                                                         @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(FOURNISSEURENDPOINT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeFournisseurDto> updateQuantiteCommande(@PathVariable("idCommande")Integer idCommande
            ,@PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(FOURNISSEURENDPOINT + "/update/fournisseur/{idCommande}/{idFournisseur}")
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable("idCommande") Integer idCommande,@PathVariable("idFournisseur")  Integer idFournisseur);

    @PatchMapping(FOURNISSEURENDPOINT + "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeFournisseurDto> updateArticle(@PathVariable("idCommande")Integer idCommande
            ,@PathVariable("idLigneCommande") Integer idLigneCommande,@PathVariable("idArticle")  Integer idArticle);

    @DeleteMapping(FOURNISSEURENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeFournisseurDto>  deleteArticle(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande") Integer idLigneCommande);


    @GetMapping(FOURNISSEURENDPOINT+"/{idCommandeFournisseur}")
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(FOURNISSEURENDPOINT+"/{codeCommandeFournisseur}")
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(FOURNISSEURENDPOINT+"/all")
    List<CommandeFournisseurDto> findAll();

    @GetMapping(FOURNISSEURENDPOINT +  "/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeFournisseurDto>> findAllLigneCommandeFournisseurByIdCommandeFournisseur(@PathVariable("idCommande") Integer idCommande);


    @DeleteMapping(FOURNISSEURENDPOINT+"/delete/{idCommandeFournisseur}")
    void delete(@PathVariable("idCommandeFournisseur") Integer id);
}

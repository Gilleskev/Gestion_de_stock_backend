package com.gilles.gestionDeStock.controller;

import com.gilles.gestionDeStock.controller.api.CommandeClientApi;
import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.model.EtatCommande;
import com.gilles.gestionDeStock.services.CommandeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeClientController  implements CommandeClientApi {

    private CommandeClientService commandeClientService;

    @Autowired
    public CommandeClientController(CommandeClientService commandeClientService) {
        this.commandeClientService = commandeClientService;
    }

    @Override
    public ResponseEntity<CommandeClientDto> save(CommandeClientDto dto) {
        //premiére maniere de le faire
        return ResponseEntity.ok(commandeClientService.save(dto));
        //deuxieme maniere de le faire en passant par le status(ok, accepted ...(200))
        //return ResponseEntity.status(HttpStatus.OK).body(commandeClientService.save(dto));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(this.commandeClientService.updateEtatCommande(idCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(this.commandeClientService.updateQuantiteCommande(idCommande, idLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateClient(Integer idCommande, Integer idClient) {
        return ResponseEntity.ok(this.commandeClientService.updateClient(idCommande, idClient));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return ResponseEntity.ok(this.commandeClientService.updateArticle(idCommande, idLigneCommande, idArticle));
    }

    @Override
    public ResponseEntity<CommandeClientDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return ResponseEntity.ok(this.commandeClientService.deleteArticle(idCommande, idLigneCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findById(Integer idCommandeclient) {
        return ResponseEntity.ok(commandeClientService.findById(idCommandeclient));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findByCode(String code) {
        return ResponseEntity.ok(commandeClientService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CommandeClientDto>> findAll() {
        return ResponseEntity.ok(commandeClientService.findAll());
    }

    @Override
    public ResponseEntity<List<LigneCommandeClientDto>> findAllLigneCommandeClientByIdCommandeClient(Integer idCommande) {
        return ResponseEntity.ok(commandeClientService.findAllLigneCommandeClientByIdCommandeClient(idCommande));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        commandeClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}

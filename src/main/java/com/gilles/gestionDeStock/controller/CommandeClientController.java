package com.gilles.gestionDeStock.controller;

import com.gilles.gestionDeStock.controller.api.CommandeClientApi;
import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.repository.CommandeClientRepository;
import com.gilles.gestionDeStock.services.CommandeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity delete(Integer id) {
        commandeClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}

package com.gilles.gestionDeStock.controller.api;

import com.gilles.gestionDeStock.dto.CommandeClientDto;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+ "/commandeclients")
public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "/commandeclients/create")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @GetMapping(APP_ROOT +  "/commandeclients/{idCommandeclient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeclient);

    @GetMapping(APP_ROOT +  "/commandeclient/{codeCommandeclient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeclient")  String code);

    @GetMapping(APP_ROOT +  "commandeclients/all")
    ResponseEntity<List<CommandeClientDto>> findAll();

    @DeleteMapping(APP_ROOT + "commandeclients/delete/{idCommandeclient}")
    ResponseEntity delete(@PathVariable("idCommandeclient") Integer id);
}

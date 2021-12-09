package com.gilles.gestionDeStock.controller.api;


import static com.gilles.gestionDeStock.utils.Constants.APP_ROOT;
import static com.gilles.gestionDeStock.utils.Constants.FOURNISSEURENDPOINT;

import com.gilles.gestionDeStock.dto.CommandeFournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(FOURNISSEURENDPOINT)
public interface CommandeFournisseurApi {

    @PostMapping(FOURNISSEURENDPOINT +"/create")
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    @GetMapping(FOURNISSEURENDPOINT+"/{idCommandeFournisseur}")
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(FOURNISSEURENDPOINT+"/{codeCommandeFournisseur}")
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(FOURNISSEURENDPOINT+"/all")
    List<CommandeFournisseurDto> findAll();

    @DeleteMapping(FOURNISSEURENDPOINT+"/delete/{idCommandeFournisseur}")
    void delete(@PathVariable("idCommandeFournisseur") Integer id);
}

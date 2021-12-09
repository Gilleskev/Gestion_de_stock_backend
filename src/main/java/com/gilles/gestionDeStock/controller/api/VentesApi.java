package com.gilles.gestionDeStock.controller.api;

import com.gilles.gestionDeStock.dto.VentesDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gilles.gestionDeStock.utils.Constants.VENTESENDPOINT;

@Api(VENTESENDPOINT)
public interface VentesApi {

    @PostMapping(VENTESENDPOINT +"create")
    VentesDto save(@RequestBody VentesDto dto);

    @GetMapping(VENTESENDPOINT + "/{idVentes}")
    VentesDto findById(@PathVariable("idVentes") Integer id);

    @GetMapping(VENTESENDPOINT + "/{codeVentes}")
    VentesDto findByCode(@PathVariable("codeVentes") String code);

    @GetMapping(VENTESENDPOINT + "/all")
    List<VentesDto> findAll();

    @DeleteMapping(VENTESENDPOINT + "/delete/{idVentes}")
    void delete(@PathVariable("idVentes") Integer id);

}

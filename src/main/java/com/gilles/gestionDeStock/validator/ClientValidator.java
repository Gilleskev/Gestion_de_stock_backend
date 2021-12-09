package com.gilles.gestionDeStock.validator;

import com.gilles.gestionDeStock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate(ClientDto clientDto) {

        List<String> errors = new ArrayList<>();

        if (clientDto == null) {
            errors.add("veuillez renseigner votre nom ");
            errors.add("veuillez renseigner votre prenom ");
            errors.add("veuillez renseigner votre Email ");
            errors.add("veuillez renseigner votre Numéro de télephone ");

            return errors;
        }
        if (!StringUtils.hasLength(clientDto.getNom())) {
            errors.add("veuillez renseigner votre nom ");
        }
        if (!StringUtils.hasLength(clientDto.getPrenom())) {
            errors.add("veuillez renseigner votre prenom ");
        }

        if (!StringUtils.hasLength(clientDto.getEmail())) {
            errors.add("veuillez renseigner votre Email ");
        }
        if (!StringUtils.hasLength(clientDto.getNumTel())) {
            errors.add("veuillez renseigner votre Numéro de télephone ");
        }


            return errors;



    }}

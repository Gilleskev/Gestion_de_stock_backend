package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.UtilisateurDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Utilisateur;
import com.gilles.gestionDeStock.repository.UtilisateurRepository;
import com.gilles.gestionDeStock.services.UtilisateurService;
import com.gilles.gestionDeStock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public  UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("User is not valid", dto);
            throw  new InvalidEntityException("le USER n'est pas Valide", ErrorCodes.UTILISATEURS_NOT_VALID, errors);
        }
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDto.toEntity(dto))
        );
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null){
            log.error("l'ID est null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(()->
                new EntityNotFoundException(
                        "aucun user avec cet ID = " + id + "n'a été trouvé dans la DB", ErrorCodes.UTILISATEURS_NOT_FOUND)
        );
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto :: fromEntity)
                .orElseThrow(()->
                        new EntityNotFoundException(
                                "aucun user avec cet email = " + email + " n'a été trouvé dans la DB", ErrorCodes.UTILISATEURS_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'ID est null");
            return;
        }
        utilisateurRepository.deleteById(id);

    }
}

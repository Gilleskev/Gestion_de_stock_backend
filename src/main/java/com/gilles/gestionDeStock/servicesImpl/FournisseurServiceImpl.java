package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.FournisseurDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Fournisseur;
import com.gilles.gestionDeStock.repository.FournisseurRepository;
import com.gilles.gestionDeStock.services.FournisseurService;
import com.gilles.gestionDeStock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurRepository fournisseurRepository;

    @Autowired
    public FournisseurServiceImpl (FournisseurRepository fournisseurRepository){
        this.fournisseurRepository = fournisseurRepository;
    }


    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Le fournisseur n'est pas valide", dto);
            throw  new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
        }
        return FournisseurDto.fromEntity(
                fournisseurRepository.save(
                        FournisseurDto.toEntity(dto)
                )
        );
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if (id == null){
            log.error("L'ID fournisseur est null");
        }
        Optional<Fournisseur> fournisseur =fournisseurRepository.findById(id);

        return fournisseurRepository.findById(id)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(()->
                new EntityNotFoundException("Aucun fournisseur avec l'ID = " + id + "n'a été trouvé dans la DB", ErrorCodes.FOURNISSEUR_NOT_FOUND)
        );
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("L'ID fournisseur est null");
            return;
        }
        fournisseurRepository.deleteById(id);
    }
}

package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.EntrepriseDto;
import com.gilles.gestionDeStock.dto.FournisseurDto;
import com.gilles.gestionDeStock.dto.RolesDto;
import com.gilles.gestionDeStock.dto.UtilisateurDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Entreprise;
import com.gilles.gestionDeStock.repository.EntrepriseRepository;
import com.gilles.gestionDeStock.repository.RolesRepository;
import com.gilles.gestionDeStock.services.EntrepriseService;
import com.gilles.gestionDeStock.services.UtilisateurService;
import com.gilles.gestionDeStock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    @Autowired
    public  EntrepriseServiceImpl (EntrepriseRepository entrepriseRepository,UtilisateurService utilisateurService,RolesRepository rolesRepository){

        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }


    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
      List<String> erros = EntrepriseValidator.validate(dto);
      if (!erros.isEmpty()){
          log.error("Entreprise n'est pas valide", dto);
          throw new InvalidEntityException("Entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, erros);
      }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(dto))
        );

        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);

        UtilisateurDto savedUser = utilisateurService.save(utilisateur);


                RolesDto rolesDto = RolesDto.builder()
                        .roleName("ADMIN")
                        .utilisateur(savedUser)
                        .build();
                rolesRepository.save(RolesDto.toEntity(rolesDto));

      return savedEntreprise;
    }

    private  UtilisateurDto fromEntreprise(EntrepriseDto dto){
        return  UtilisateurDto.builder()
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getCodeFisscal())
                .email(dto.getEmail())
                .motDePasse(generateRandomPassword())
                .entreprise(dto)
                .dateDeNaissance(Instant.now())
                .photo(dto.getPhoto())
                .build();
    }

    private String generateRandomPassword(){return "som3R@ndomP@$$word";}

    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null){
            log.error("L'ID entreprise  est null");
            return  null;
        }

        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);

        return entrepriseRepository.findById(id)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(()->
                new EntityNotFoundException("Aucune entreprise avec l'ID = " + id +
                        "n'a été trouvé dans la BD", ErrorCodes.ENTREPRISE_NOT_FOUND));
    }

    @Override
    public EntrepriseDto findBySiret(String siret) {
        if (!StringUtils.hasLength(siret)){
            log.error(" Le siret de l'entreprise est null");
            return null;
        }

        Optional<Entreprise> entreprise = entrepriseRepository.findEntrepriseBySiret(siret);

        return entrepriseRepository.findEntrepriseBySiret(siret)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(()->
                new EntityNotFoundException(
                        "Aucune entreprise avec le siret = " + siret +
                                "n'a été trouvé dans la BD", ErrorCodes.ARTICLE_NOT_FOUND
                ));
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto :: fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("L'ID entreprise  est null");
            return;
        }
        entrepriseRepository.deleteById(id);

    }
}

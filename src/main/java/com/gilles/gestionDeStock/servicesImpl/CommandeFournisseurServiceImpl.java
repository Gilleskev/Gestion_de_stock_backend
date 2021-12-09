package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.CommandeFournisseurDto;
import com.gilles.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Article;
import com.gilles.gestionDeStock.model.CommandeFournisseur;
import com.gilles.gestionDeStock.model.Fournisseur;
import com.gilles.gestionDeStock.model.LigneCommandeFournisseur;
import com.gilles.gestionDeStock.repository.ArticleRepository;
import com.gilles.gestionDeStock.repository.CommandeFournisseurReposiory;
import com.gilles.gestionDeStock.repository.FournisseurRepository;
import com.gilles.gestionDeStock.repository.LigneCommandeFournisseurRepository;
import com.gilles.gestionDeStock.services.CommandeFournisseurService;
import com.gilles.gestionDeStock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurReposiory commandeFournisseurReposiory;
    private ArticleRepository articleRepository;
    private FournisseurRepository fournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurReposiory commandeFournisseurReposiory, ArticleRepository articleRepository, FournisseurRepository fournisseurRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository) {
        this.commandeFournisseurReposiory = commandeFournisseurReposiory;
        this.articleRepository = articleRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {

        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("la commande fournisseur n'est pas valide");
            throw new InvalidEntityException("la commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());

        if (fournisseur.isPresent()){
            log.warn("Aucun fournisseur avec ID {} n'a été trouvé dans la DB "+dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur avec l'ID "+dto.getFournisseur().getId() + "n'as été trouvé dans la BDD",ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        List<String> fournisseurErrors = new ArrayList<>();
        if (dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs->{
                if (ligCmdFrs.getArticle() !=null){
                    Optional<Article> article = articleRepository.findById(ligCmdFrs.getArticle().getId());
                    if (article.isEmpty()) {
                        fournisseurErrors.add("L'article avec l'ID " + ligCmdFrs.getArticle().getId() + " n'existe pas");
                    }
                }else {
                    fournisseurErrors.add("Imposible d'enregistrer une commande avec un article null");
                }
            });
        }
        if (!fournisseurErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("L'article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, fournisseurErrors);
        }

        CommandeFournisseur savedCmdFrs = commandeFournisseurReposiory.save(CommandeFournisseurDto.toEntity(dto));
        if (dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs->{
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrs);
                ligneCommandeFournisseur.setCommandeFournisseur(savedCmdFrs);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }
        return CommandeFournisseurDto.fromEntity(savedCmdFrs);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null){
            log.error("L'ID de la commande fournisseur est null");
            return null;
        }
        return commandeFournisseurReposiory.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune commande fournisseur avec l'ID " + id +" n'est trouvé", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("le code de la commande founisseur  est null");
            return null;
        }
        return commandeFournisseurReposiory.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune commande fournisseur avec le code " + code +" n'est trouvé", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurReposiory.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("l'ID de la commande forunisseur est null");
            return ;
        }
        commandeFournisseurReposiory.findById(id);
    }
}

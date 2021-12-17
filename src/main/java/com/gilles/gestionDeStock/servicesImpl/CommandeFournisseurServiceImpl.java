package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.*;
import com.gilles.gestionDeStock.dto.CommandeFournisseurDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.exception.InvalidOperationException;
import com.gilles.gestionDeStock.model.*;
import com.gilles.gestionDeStock.repository.ArticleRepository;
import com.gilles.gestionDeStock.repository.CommandeFournisseurReposiory;
import com.gilles.gestionDeStock.repository.FournisseurRepository;
import com.gilles.gestionDeStock.repository.LigneCommandeFournisseurRepository;
import com.gilles.gestionDeStock.services.CommandeFournisseurService;
import com.gilles.gestionDeStock.validator.ArticleValidator;
import com.gilles.gestionDeStock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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
            throw new InvalidEntityException("la commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
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
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'etat de la commande fournisseur est null");
            throw new InvalidOperationException("Imposible de modifier l'état de la commande avec un état null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UPDATABLE);
        }

        CommandeFournisseurDto commandeFourniesseur = checkEtatCommande(idCommande);
        commandeFourniesseur.setEtatCommande(etatCommande);

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurReposiory.save(CommandeFournisseurDto.toEntity(commandeFourniesseur))
        );
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) ==0){
            throw new InvalidOperationException("Imposible de modifier la quantite de la commande avec une quantite null iu ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UPDATABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();

        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        checkIdCommande(idCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);

        if (fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun fournisseur avec l'id " + idFournisseur + "trouvé dans la BDD", ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }


        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));
        return CommandeFournisseurDto.fromEntity(commandeFournisseurReposiory.save(
                CommandeFournisseurDto.toEntity(commandeFournisseur)
        ));
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        // Vérifie que la commande existe
        checkIdCommande(idCommande);
        // Vérifie que la ligne de commande existe
        checkIdLigneCommande(idLigneCommande);
        // Vérifie que l'article existe
        checkIdArticle(idArticle, "nouvel");
        // Vérifie l'état de la commande
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        // Vérifie et cherche la ligne de commande
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur= findLigneCommandeFournisseur(idLigneCommande);

        // Vérifie et cherche l'article
        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional == null){
            throw new EntityNotFoundException("Aucune article n'a été trouvé avec l'id " + idArticle , ErrorCodes.ARTICLE_NOT_FOUND);
        }
        // Valide le nouvel article à modifier
        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()){
            throw new InvalidEntityException("Article invalide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        // set ma ligne de commande et l'enregistre
        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
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
    public List<LigneCommandeFournisseurDto> findAllLigneCommandeFournisseurByIdCommandeFournisseur(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
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

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        // verifie la ligne de commande fournisseur
        findLigneCommandeFournisseur(idLigneCommande);

        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    private void checkIdCommande(Integer idCommande){
        if (idCommande == null){
            log.error("L'Id de la commande fournisseur est null");
            throw new InvalidOperationException("Imposible de modifier l'etat de la commande avec un id null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UPDATABLE);
        }
    }

    private void  checkIdLigneCommande(Integer idLigneCommande){
        if (idLigneCommande == null){
            log.error("L'Id de la ligne commande fournisseur est null");
            throw new InvalidOperationException("Imposible de modifier l'etat de la  commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UPDATABLE);
        }
    }

    private void  checkIdArticle(Integer idArticle, String msg){
        if (idArticle == null){
            log.error("L'Id de "+msg+" article est null");
            throw new InvalidOperationException("Imposible de modifier l'etat de la commande l'article avec "+ msg +" Id article null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UPDATABLE);
        }
    }
    
    private CommandeFournisseurDto checkEtatCommande(Integer idCommande){
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()){
            throw new InvalidOperationException("Imposible de modifier la commande lorsqu'elle est livrée", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UPDATABLE);
        }
        return commandeFournisseur;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande){
        Optional<LigneCommandeFournisseur> ligneCommandefournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);

        if (ligneCommandefournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucune ligne commande fournisseur avec l'ID " + idLigneCommande + "trouvé dans la BDD", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }

        return ligneCommandefournisseurOptional;
    }

}

package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.ClientDto;
import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.exception.InvalidOperationException;
import com.gilles.gestionDeStock.model.*;
import com.gilles.gestionDeStock.repository.ArticleRepository;
import com.gilles.gestionDeStock.repository.ClientRepository;
import com.gilles.gestionDeStock.repository.CommandeClientRepository;
import com.gilles.gestionDeStock.repository.LigneCommandeClientRepository;
import com.gilles.gestionDeStock.services.CommandeClientService;
import com.gilles.gestionDeStock.validator.ArticleValidator;
import com.gilles.gestionDeStock.validator.CommandeClientValidator;
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
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,
                                     LigneCommandeClientRepository ligneCommandeClientRepository, ClientRepository clientRepository, ArticleRepository articleRepository) {
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {

        List<String> errors = CommandeClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("La commande client n'est pas valide");
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()){
            throw new InvalidOperationException("Imposible de modifier la commande lorsqu'elle est livrée", ErrorCodes.COMMANDE_CLIENT_NOT_UPDATABLE, errors);
        }

        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (client.isPresent()) {
            log.warn("L'ID du client {} n'a pas été trouvé dans la DB", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec l'ID " + dto.getClient().getId() + "n'as été trouvé dans la BDD", ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();
        if (dto.getLigneCommandeClents() != null) {
            dto.getLigneCommandeClents().forEach(ligCmClt -> {
                if (ligCmClt.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmClt.getArticle().getId());
                if (article.isEmpty()) {
                    articleErrors.add("L'article avec l'ID " + ligCmClt.getArticle().getId() + " n'existe pas");
                }
            } else {
                articleErrors.add("Imposible d'enregistrer une commande avec un article null");
            }
            });
        }
        if (!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("L'article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        if (dto.getLigneCommandeClents() != null) {
            dto.getLigneCommandeClents().forEach(ligCmdClt -> {
                LigneCommandeClent ligneCommandeClent = LigneCommandeClientDto.toEntity(ligCmdClt);
                ligneCommandeClent.setCommandeClient(savedCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClent);
            });
        }

        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'etat de la commande client est null");
            throw new InvalidOperationException("Imposible de modifier l'état de la commande avec un état null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UPDATABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        commandeClient.setEtatCommande(etatCommande);

        return CommandeClientDto.fromEntity(
               commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) ==0){
            throw new InvalidOperationException("Imposible de modifier la quantite de la commande avec une quantite null iu ZERO",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UPDATABLE);
        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCommandeClent> ligneCommandeClentOptional = findLigneCommandeClient(idLigneCommande);

        LigneCommandeClent ligneCommandeClent = ligneCommandeClentOptional.get();

        ligneCommandeClent.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClent);
        return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        checkIdCommande(idCommande);

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<Client> clientOptional = clientRepository.findById(idClient);

        if (clientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun client avec l'id " + idClient + "trouvé dans la BDD", ErrorCodes.CLIENT_NOT_FOUND);
        }


        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));
        return CommandeClientDto.fromEntity(commandeClientRepository.save(
                CommandeClientDto.toEntity(commandeClient)
        ));
    }


    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        // Vérifie que la commande existe
        checkIdCommande(idCommande);
        // Vérifie que la ligne de commande existe
        checkIdLigneCommande(idLigneCommande);
        // Vérifie que l'article existe
        checkIdArticle(idArticle, "nouvel");
        // Vérifie l'état de la commande
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        // Vérifie et cherche la ligne de commande
        Optional<LigneCommandeClent> ligneCommandeClent = findLigneCommandeClient(idLigneCommande);

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
        LigneCommandeClent ligneCommandeClentToSaved = ligneCommandeClent.get();
        ligneCommandeClentToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClentToSaved);

        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        // verifie la ligne de commande client
        findLigneCommandeClient(idLigneCommande);

        ligneCommandeClientRepository.deleteById(idLigneCommande);

        return commandeClient;
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("L'Id de la commande client est null");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException("Auccune commande client avec l'ID " + id + "trouvé dans la BDD", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Le code de la commande client est null");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException("Aucune commande client avec " + code + "trouvé dans la BDD", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<LigneCommandeClientDto> findAllLigneCommandeClientByIdCommandeClient(Integer idCommande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'Id de la commande client est null");
            return;
        }
        commandeClientRepository.deleteById(id);
    }

    private void checkIdCommande(Integer idCommande){
        if (idCommande == null){
            log.error("L'Id de la commande client est null");
            throw new InvalidOperationException("Imposible de modifier l'etat de la commande avec un id null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UPDATABLE);
        }
    }

    private void  checkIdLigneCommande(Integer idLigneCommande){
        if (idLigneCommande == null){
            log.error("L'Id de la ligne commande client est null");
            throw new InvalidOperationException("Imposible de modifier l'etat de la  commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UPDATABLE);
        }
    }

    private void  checkIdArticle(Integer idArticle, String msg){
        if (idArticle == null){
            log.error("L'Id de "+msg+" article est null");
            throw new InvalidOperationException("Imposible de modifier l'etat de la commande l'article avec "+ msg +" Id article null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UPDATABLE);
        }
    }
    private  CommandeClientDto checkEtatCommande(Integer idCommande){
        CommandeClientDto commandeClient = findById(idCommande);
        if (commandeClient.isCommandeLivree()){
            throw new InvalidOperationException("Imposible de modifier la commande lorsqu'elle est livrée", ErrorCodes.COMMANDE_CLIENT_NOT_UPDATABLE);
        }
        return commandeClient;
    }

    private Optional<LigneCommandeClent> findLigneCommandeClient(Integer idLigneCommande){
        Optional<LigneCommandeClent> ligneCommandeClentOptional = ligneCommandeClientRepository.findById(idLigneCommande);

        if (ligneCommandeClentOptional.isEmpty()){
            throw new EntityNotFoundException("Aucune ligne commande client avec l'ID " + idLigneCommande + "trouvé dans la BDD", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }

        return ligneCommandeClentOptional;
    }


}

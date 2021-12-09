package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.CommandeClientDto;
import com.gilles.gestionDeStock.dto.LigneCommandeClientDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Article;
import com.gilles.gestionDeStock.model.Client;
import com.gilles.gestionDeStock.model.CommandeClient;
import com.gilles.gestionDeStock.model.LigneCommandeClent;
import com.gilles.gestionDeStock.repository.ArticleRepository;
import com.gilles.gestionDeStock.repository.ClientRepository;
import com.gilles.gestionDeStock.repository.CommandeClientRepository;
import com.gilles.gestionDeStock.repository.LigneCommandeClientRepository;
import com.gilles.gestionDeStock.services.CommandeClientService;
import com.gilles.gestionDeStock.validator.CommandeClientValidator;
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
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("L'Id de la commande est null");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException("Auccune commande client avec " + id + "trouvé dans la BDD", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
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
}

package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.LigneVenteDto;
import com.gilles.gestionDeStock.dto.VentesDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Article;
import com.gilles.gestionDeStock.model.LigneVente;
import com.gilles.gestionDeStock.model.Ventes;
import com.gilles.gestionDeStock.repository.ArticleRepository;
import com.gilles.gestionDeStock.repository.LigneVenteRepository;
import com.gilles.gestionDeStock.repository.VenteRepository;
import com.gilles.gestionDeStock.services.VentesService;
import com.gilles.gestionDeStock.validator.VenteValidator;
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
public class VentesServiceImpl implements VentesService {

    private VenteRepository venteRepository;
    private ArticleRepository articleRepository;
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    public VentesServiceImpl(VenteRepository venteRepository, ArticleRepository articleRepository,
                             LigneVenteRepository ligneVenteRepository) {
        this.venteRepository = venteRepository;
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    }

    @Override
    public VentesDto save(VentesDto dto) {
        List<String> errors = VenteValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("La vente n'est pas valide");
            throw new InvalidEntityException("Vente non valide", ErrorCodes.VENTES_NOT_VALID, errors);
        }
        List<String> articleErrors = new ArrayList<>();

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()){
                articleErrors.add("Aucun article avec l'ID "+ ligneVenteDto.getArticle().getId()+ " n'est trouvé dans la BDD");
            }
        });

        if (!articleErrors.isEmpty()){
            log.error("Un ou plusieurs articles introuvés dans la BD {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles introuvés dans la BDD", ErrorCodes.VENTES_NOT_VALID, errors);
        }

        Ventes savedVentes = venteRepository.save(VentesDto.toEntity(dto));

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVentes(savedVentes);
            ligneVenteRepository.save(ligneVente);
        });
        return VentesDto.fromEntity(savedVentes);
    }

    @Override
    public VentesDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de la vente est null");
            return null;
        }
        return venteRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune vente avec l'ID = " + id + " n'a été trouvé dans la BD", ErrorCodes.VENTES_NOT_FOUND)
                );
    }

    @Override
    public VentesDto findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("Le code de la vente est null");
            return null;
        }

        return venteRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune vente avec l'ID = " + code + "n'a été trouvé dans la BD", ErrorCodes.VENTES_NOT_FOUND)
                );
    }

    @Override
    public List<VentesDto> findAll() {
        return venteRepository.findAll().stream()
                .map(VentesDto :: fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de la vente est null");
            return;
        }
        venteRepository.deleteById(id);
    }
}

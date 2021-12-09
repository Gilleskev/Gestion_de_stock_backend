package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.CategoryDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Category;
import com.gilles.gestionDeStock.repository.CategoryRepository;
import com.gilles.gestionDeStock.services.CategoryService;
import com.gilles.gestionDeStock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> erros = CategoryValidator.validate(dto);
        if (!erros.isEmpty()) {
            log.error("Categorie non valide", dto);
            throw new InvalidEntityException("Category non valide", ErrorCodes.CATEGORY_NOT_VALID, erros);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(dto))
        );
    }

    @Override
    public CategoryDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de la categorie est null");
            return null;
        }

        return categoryRepository.findById(id)
                .map(CategoryDto :: fromEntity)
                .orElseThrow(()->
                                new EntityNotFoundException("Aucune categorie avec l'id = " + id + " n'a été trouvé dans la DB", ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    @Override
    public CategoryDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("L'ID de la categorie est null");
            return null;
        }
        return categoryRepository.findCategoriesByCode(code)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune categorie avec le code = " + code + " n'a été trouvé dans la DB", ErrorCodes.CATEGORY_NOT_FOUND)
        );
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de la categorie est null");
            return;
        }
        categoryRepository.deleteById(id);
    }
}

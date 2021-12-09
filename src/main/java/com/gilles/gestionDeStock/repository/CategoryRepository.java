package com.gilles.gestionDeStock.repository;

import com.gilles.gestionDeStock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    Optional<Category> findCategoriesByCode(String code);
}

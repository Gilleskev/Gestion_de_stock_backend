package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.CategoryDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.services.CategoryService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService service;

    @Test
    public void shouldSaveCategoryWithSuccess(){
       CategoryDto expectedCategory =  CategoryDto.builder()
                .code("Cat test")
                .designation("Designation test")
                .idEntreprise(1)
                .build();

       CategoryDto savedCategory = service.save(expectedCategory);

       Assertions.assertNotNull(savedCategory);
       Assertions.assertNotNull(savedCategory.getId());
       Assertions.assertEquals(expectedCategory.getCode(), savedCategory.getCode());
       Assertions.assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());
       Assertions.assertEquals(expectedCategory.getIdEntreprise(), savedCategory.getIdEntreprise());
    }

    @Test
    public void shouldUpdateCategoryWithSuccess(){
        CategoryDto expectedCategory =  CategoryDto.builder()
                .code("Cat test")
                .designation("Designation test")
                .idEntreprise(1)
                .build();

        CategoryDto savedCategory = service.save(expectedCategory);

        CategoryDto categoryToUpdate = savedCategory;
        categoryToUpdate.setCode("Cat update");

        savedCategory = service.save(categoryToUpdate);

        Assertions.assertNotNull(categoryToUpdate);
        Assertions.assertNotNull(categoryToUpdate.getId());
        Assertions.assertEquals(categoryToUpdate.getCode(), savedCategory.getCode());
        Assertions.assertEquals(categoryToUpdate.getDesignation(), savedCategory.getDesignation());
        Assertions.assertEquals(categoryToUpdate.getIdEntreprise(), savedCategory.getIdEntreprise());
    }

    @Test
    public  void shouldThrowInvalidEntityException(){
        CategoryDto expectedCategory = CategoryDto.builder().build();

        InvalidEntityException expectedException = Assertions.assertThrows(InvalidEntityException.class, ()-> service.save(expectedCategory));

        Assertions.assertEquals(ErrorCodes.CATEGORY_NOT_VALID, expectedException.getErrorCode());
        Assertions.assertEquals(1, expectedException.getErrors().size());
        Assertions.assertEquals("veuillez renseignez le code de la categorie", expectedException.getErrors().get(0));
    }

    @Test
    public  void shouldThrowNotFoundException(){
        CategoryDto expectedCategory = CategoryDto.builder().build();

        EntityNotFoundException expectedException = Assertions.assertThrows(EntityNotFoundException.class, ()-> service.findById(0));

        Assertions.assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCode());
        Assertions.assertEquals("any category whit ID = 0 has find in DB", expectedException.getMessage());

    }

    @Test(expected = EntityNotFoundException.class)
    public  void shouldThrowNotFoundException2(){
       service.findById(0);
    }

}
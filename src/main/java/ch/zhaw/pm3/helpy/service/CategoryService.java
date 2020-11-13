package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.CategoryDTO;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ch.zhaw.pm3.helpy.service.DTOMapper.*;

/**
 * Service for the categories.
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Returns all categories.
     * @return list of {@link CategoryDTO}
     */
    public Set<CategoryDTO> getAllCategories() {
        return mapCategoriesToDTOs(categoryRepository.findAll());
    }

    /**
     * Returns a {@link CategoryDTO} by name
     * @param name of the {@link Category}
     * @return a {@link Category}
     */
    public CategoryDTO getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.findById(name);
        if (category.isEmpty()) throw new RecordNotFoundException(name);
        return mapCategoryToDTO(category.get());
    }

    /**
     * Returns a list of related categories.
     * @param name of the {@link Category}
     * @return list of related categories
     */
    public Set<CategoryDTO> getRelatedCategories(String name) {
        if (categoryRepository.findById(name).isEmpty()) throw new RecordNotFoundException(name);
        return mapCategoriesToDTOs(categoryRepository.findRelatedCategories(name));
    }

    /**
     * Creates a {@link CategoryDTO}
     * @param category the new {@link CategoryDTO}
     */
    public void createCategory(CategoryDTO category) {
        categoryRepository.save(mapDTOToCategory(category));
    }

    /**
     * Updates a {@link Category}
     * @param category the updated Category
     */
    public void updateCategory(CategoryDTO category) {
        Optional<Category> oldCat = categoryRepository.findById(category.getName());
        if (oldCat.isEmpty()) throw new RecordNotFoundException(category.getName());
        categoryRepository.save(mapDTOToCategory(category));
    }

    /**
     * Deletes a {@link Category} by it's name
     * @param name of the {@link Category}
     * @return the deleted {@link Category}
     */
    public CategoryDTO deleteCategory(String name) {
        Optional<Category> category = categoryRepository.findById(name);
        if (category.isEmpty()) throw new RecordNotFoundException(name);
        categoryRepository.delete(category.get());
        return mapCategoryToDTO(category.get());
    }

}

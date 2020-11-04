package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
     * @return list of {@link Category}
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Returns a {@link Category} by name
     * @param name of the {@link Category}
     * @return a {@link Category}
     */
    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByName(name);
        if (category == null) throw new RecordNotFoundException(name);
        return category;
    }

    /**
     * Returns a list of related categories.
     * @param name of the {@link Category}
     * @return list of related categories
     */
    public List<Category> getRelatedCategories(String name) {
        if (categoryRepository.findCategoryByName(name) == null) throw new RecordNotFoundException(name);
        return categoryRepository.findRelatedCategories(name);
    }

    /**
     * Creates a {@link Category}
     * @param category the new {@link Category}
     */
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Updates a {@link Category}
     * @param category the updated Category
     */
    public void updateCategory(Category category) {
        Category oldCat = categoryRepository.findCategoryByName(category.getName());
        if (oldCat == null) throw new RecordNotFoundException(category.getName());
        categoryRepository.save(category);
    }

    /**
     * Deletes a {@link Category} by it's name
     * @param name of the {@link Category}
     * @return the deleted {@link Category}
     */
    public Category deleteCategory(String name) {
        Category category = categoryRepository.findCategoryByName(name);
        if (category == null) throw new RecordNotFoundException(name);
        categoryRepository.delete(category);
        return category;
    }

}

package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        Optional<Category> category = categoryRepository.findById(name);
        if (category.isEmpty()) throw new RecordNotFoundException(name);
        return category.get();
    }

    /**
     * Returns a list of related categories.
     * @param name of the {@link Category}
     * @return list of related categories
     */
    public List<Category> getRelatedCategories(String name) {
        if (categoryRepository.findById(name).isEmpty()) throw new RecordNotFoundException(name);
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
        Optional<Category> oldCat = categoryRepository.findById(category.getName());
        if (oldCat.isEmpty()) throw new RecordNotFoundException(category.getName());
        categoryRepository.save(category);
    }

    /**
     * Deletes a {@link Category} by it's name
     * @param name of the {@link Category}
     * @return the deleted {@link Category}
     */
    public Category deleteCategory(String name) {
        Optional<Category> category = categoryRepository.findById(name);
        if (category.isEmpty()) throw new RecordNotFoundException(name);
        categoryRepository.delete(category.get());
        return category.get();
    }

}

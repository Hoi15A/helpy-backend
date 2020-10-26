package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByName(name);
        if (category == null) throw new RecordNotFoundException(name);
        return category;
    }

    public List<Category> getRelatedCategories(String name) {
        if (categoryRepository.findCategoryByName(name) == null) throw new RecordNotFoundException(name);
        return categoryRepository.findRelatedCategories(name);
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        Category oldCat = categoryRepository.findCategoryByName(category.getName());
        if (oldCat == null) throw new RecordNotFoundException(category.getName());
        categoryRepository.save(category);
    }

    public Category deleteCategory(String name) {
        Category category = categoryRepository.findCategoryByName(name);
        if (category == null) throw new RecordNotFoundException(name);
        categoryRepository.delete(category);
        return category;
    }

}

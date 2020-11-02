package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This class is for the manipulation of the {@link Category} model.
 */
@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Autowires the user service for the category management.
     * @param categoryService {@link CategoryService}
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns all categories from the database as json.
     * @return ResponseEntity<List<Category>>
     */
    @GetMapping("all")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Takes a {@link Category} in json notation and saves it to the database.
     * The same {@link Category} will be returned after creation.
     * @param category takes a {@link Category} object.
     * @return ResponseEntity<Category>
     */
    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@Valid @RequestBody final Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok(category);
    }

    /**
     * Takes a {@link Category} in json notation and updates the database entry with the same name.
     * The saved {@link Category} will be returned after the update.
     * @param category takes a {@link Category} object.
     * @return ResponseEntity<Category>
     */
    @PutMapping("update")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody final Category category) {
        categoryService.updateCategory(category);
        return ResponseEntity.ok(category);
    }

    /**
     * Takes a category name and deletes it from the database.
     * The deleted {@link Category} will be returned.
     * @param name of the {@link Category}
     * @return ResponseEntity<Category>
     */
    @DeleteMapping("remove/{category}")
    public ResponseEntity<Category> removeCategory(@PathVariable(name = "category") final String name) {
        return ResponseEntity.ok(categoryService.deleteCategory(name));
    }

    /**
     * Takes a category name and sear
     * @param name of the {@link Category}
     * @return ResponseEntity<Category>
     */
    @GetMapping("{category}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable("category") final String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    /**
     * Searches a category by name and extracts the related categories.
     * Returns the related categories as json list.
     * @param category string of the category name
     * @return ResponseEntity<List<Category>>
     */
    @GetMapping("related/{category}")
    public ResponseEntity<List<Category>> getRelatedCategories(@PathVariable("category") final String category) {
        return ResponseEntity.ok(categoryService.getRelatedCategories(category));
    }
}


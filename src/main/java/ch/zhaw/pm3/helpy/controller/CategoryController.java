package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.category.CategoryDTO;
import ch.zhaw.pm3.helpy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * This class is for the manipulation of the {@link Category} model.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Returns all {@link CategoryDTO} from the database.
     * @return ResponseEntity<List<Category>>
     */
    @GetMapping("all")
    public ResponseEntity<Set<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Takes a {@link CategoryDTO} and saves it to the database.
     * The same {@link CategoryDTO} will be returned after creation.
     * @param category takes a {@link CategoryDTO} object.
     * @return ResponseEntity<Category>
     */
    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody final CategoryDTO category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok(category);
    }

    /**
     * Takes a {@link CategoryDTO} and updates the database entry with the same name.
     * The saved {@link CategoryDTO} will be returned after the update.
     * @param category takes a {@link CategoryDTO} object.
     * @return ResponseEntity<Category>
     */
    @PutMapping("update")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody final CategoryDTO category) {
        categoryService.updateCategory(category);
        return ResponseEntity.ok(category);
    }

    /**
     * Takes a category name and deletes the category object from the database.
     * The deleted {@link CategoryDTO} will be returned.
     * @param name of the {@link CategoryDTO}
     * @return ResponseEntity<CategoryDTO>
     */
    @DeleteMapping("remove/{category}")
    public ResponseEntity<CategoryDTO> removeCategory(@PathVariable(name = "category") final String name) {
        return ResponseEntity.ok(categoryService.deleteCategory(name));
    }

    /**
     * Takes a category name and returns the the found {@link CategoryDTO} object from the database.
     * @param name of the {@link CategoryDTO}
     * @return ResponseEntity<CategoryDTO>
     */
    @GetMapping("{category}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable("category") final String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    /**
     * Searches a category by name and extracts the related categories.
     * Returns the related categories as json list.
     * @param category string of the category name
     * @return ResponseEntity<List<CategoryDTO>>
     */
    @GetMapping("related/{category}")
    public ResponseEntity<Set<CategoryDTO>> getRelatedCategories(@PathVariable("category") final String category) {
        return ResponseEntity.ok(categoryService.getRelatedCategories(category));
    }
}


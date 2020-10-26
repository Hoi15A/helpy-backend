package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@Valid @RequestBody final Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok(category);
    }

    @PutMapping("update")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody final Category category) {
        categoryService.updateCategory(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("remove/{category}")
    public ResponseEntity<Category> removeCategory(@PathVariable(name = "category") final String name) {
        return ResponseEntity.ok(categoryService.deleteCategory(name));
    }

    @GetMapping("{category}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable("category") final String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @GetMapping("related/{category}")
    public ResponseEntity<List<Category>> getRelatedCategories(@PathVariable("category") final String category) {
        return ResponseEntity.ok(categoryService.getRelatedCategories(category));
    }
}


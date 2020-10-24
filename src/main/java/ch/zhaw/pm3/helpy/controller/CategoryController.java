package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("all")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@Valid @RequestBody final Category category) {
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }

    @PutMapping("update")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody final Category newCat) {
        Category oldCat = categoryRepository.findCategoryByName(newCat.getName());
        if (oldCat == null) return ResponseEntity.notFound().build();
        categoryRepository.save(newCat);
        return ResponseEntity.ok(newCat);
    }

    @DeleteMapping("remove/{category}")
    public ResponseEntity<Category> removeCategory(@PathVariable(name = "category") final String name) {
        Category category = categoryRepository.findCategoryByName(name);
        if (category == null) return ResponseEntity.notFound().build();
        categoryRepository.delete(category);
        return ResponseEntity.ok(category);
    }

    @GetMapping("{category}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable("category") final String name) {
        Category category = categoryRepository.findCategoryByName(name);
        return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(category);
    }

    @GetMapping("related/{category}")
    public ResponseEntity<List<Category>> getRelatedCategories(@PathVariable("category") final String category) {
        if (categoryRepository.findCategoryByName(category) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryRepository.findRelatedCategories(category));
    }
}


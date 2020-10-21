package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.Category;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    private Gson gson = new Gson();

    @GetMapping("all")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.badRequest().body(categoryRepository.findAll());
    }

    @PostMapping("add")
    public ResponseEntity<Category> createCategory(@RequestBody final String body) {
        Category category = gson.fromJson(body, Category.class);
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }

    @PutMapping("update")
    public ResponseEntity<Category> updateCategory(@RequestBody final String body) {
        Category newCat = gson.fromJson(body, Category.class);
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


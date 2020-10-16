package ch.zhaw.pm3.helpy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    //+ getCategories(): ResponseEntity
    //+ getCategorieByName(name: String): ResponseEntity
    //+ getRelatedCategories(category: Category): ResponseEntity

    @GetMapping("all")
    public ResponseEntity<List<String>> getCategories() {
        //todo
        List<String> categories = new ArrayList<>();
        categories.add("Sprache");
        return ResponseEntity.badRequest().body(categories);
    }

    @GetMapping("{category}")
    public ResponseEntity<String> getCategoryByName(@PathVariable("category") final String category) {
        //todo
        return ResponseEntity.badRequest().body("Not Implemented");
    }

    @GetMapping("related/{category}")
    public ResponseEntity<List<String>> getRelatedCategories(@PathVariable("category") final String category) {
        //todo
        List<String> relatedCategories = new ArrayList<>();
        relatedCategories.add("SBB");
        return ResponseEntity.badRequest().body(relatedCategories);
    }
}


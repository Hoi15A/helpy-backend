package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class is for the manipulation of the {@link Tag} model.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/tag")
public class TagController {

    private final TagService tagService;

    /**
     * Returns all {@link Tag} from the database.
     * @return ResponseEntity<List<Tag>>
     */
    @GetMapping("all")
    public ResponseEntity<List<Tag>> getCategories() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}

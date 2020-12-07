package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service for the tags.
 */
@RequiredArgsConstructor
@Transactional
@Service
public class TagService {
    private final TagRepository tagRepository;

    /**
     * Get a list of all tags in the database
     * @return a list of {@link Tag}
     */
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}

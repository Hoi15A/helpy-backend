package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.category.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
}

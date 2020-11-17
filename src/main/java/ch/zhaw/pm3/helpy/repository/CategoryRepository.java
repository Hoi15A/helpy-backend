package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, String> {
    /**
     * Query to get the set of related categories attribute from the given category
     * @param category of which to get related categories
     * @return set of related categories
     */
    @Query("select c.listOfRelated from Category c where c.name=?1")
    Set<Category> findRelatedCategories(String category);
}

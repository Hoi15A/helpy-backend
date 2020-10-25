package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Query to get a Category from the database matching the given name
     * @param name of category to get
     * @return Category with title matching name
     */
    @Query("select c from Category c where c.name=?1")
    Category findCategoryByName(String name);

    /**
     * Query to get the list of related categories attribute from the given category
     * @param category of which to get related categories
     * @return List of related categories
     */
    @Query("select c.listOfRelated from Category c where c.name=?1")
    List<Category> findRelatedCategories(String category);
}

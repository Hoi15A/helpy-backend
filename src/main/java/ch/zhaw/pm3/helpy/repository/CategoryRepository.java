package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.name=?1")
    Category findCategoryByName(String name);

    @Query("select c.listOfRelated from Category c where c.name=?1")
    List<Category> findRelatedCategories(String category);
}

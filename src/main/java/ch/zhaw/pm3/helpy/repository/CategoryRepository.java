package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from Category as c where c.name=?1", nativeQuery = true)
    Category findCategoryByName(String name);

    @Query("select c.listOfRelated from Category as c where c.name=?1")
    List<Category> findRelatedCategories(String category);
}

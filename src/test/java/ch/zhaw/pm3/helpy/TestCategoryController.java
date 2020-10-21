package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.controller.CategoryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCategoryController {

    @Autowired
    private CategoryController categoryController;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetCategoryByName() throws Exception {

    }

    @Test
    public void testCreateCategory() throws Exception {

    }

    @Test
    public void testGetCategories() throws Exception{

    }

    @Test
    public void testUpdateCategory() throws Exception {

    }

    @Test
    public void testUpdateCategoryNotFound() throws Exception {

    }

    @Test
    public void testDeleteCategory() throws Exception {

    }

    @Test
    public void testDeleteCategoryNotFound() throws Exception {

    }

    @Test
    public void testGetRelatedCategories() throws Exception {

    }

    @Test
    public void testGetRelatedCategoriesNotFound() throws Exception {

    }
}
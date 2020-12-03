package ch.zhaw.pm3.helpy.test.controller;

import ch.zhaw.pm3.helpy.config.Profiles;
import ch.zhaw.pm3.helpy.controller.CategoryController;
import ch.zhaw.pm3.helpy.model.category.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(Profiles.NO_AUTH)
class CategoryControllerTest {

    static final String REQUEST_MAPPING = "/api/category";
    static final String EXISTING_CATEGORY_NAME_IN_USE = "Sprache";
    static final String EXISTING_CATEGORY_NAME_NOT_IN_USE = "Geistig";
    static final String RANDOM_TEST_STRING = "test";

    @Autowired
    CategoryController categoryController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(categoryController);
    }

    @Test
    void testGetCategoryByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{category}", EXISTING_CATEGORY_NAME_IN_USE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(EXISTING_CATEGORY_NAME_IN_USE));
    }

    @Test
    void testCreateCategory() throws Exception {
        Category testCat = new Category(RANDOM_TEST_STRING);
        mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(asJsonString(testCat))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(RANDOM_TEST_STRING));
    }

    @Test
    void testGetCategories() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").isNotEmpty());
    }

    @Test
    void testUpdateCategory() throws Exception {
        Category testCat = new Category(EXISTING_CATEGORY_NAME_IN_USE);
        mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update")
                .content(asJsonString(testCat))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value(RANDOM_TEST_STRING));
    }

    @Test
    void testUpdateCategoryNotFound() throws Exception {
        Category testCat = new Category(RANDOM_TEST_STRING);
        mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update")
                .content(asJsonString(testCat))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{category}", EXISTING_CATEGORY_NAME_NOT_IN_USE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(EXISTING_CATEGORY_NAME_NOT_IN_USE));
    }

    @Test
    void testDeleteCategoryNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{category}", RANDOM_TEST_STRING)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetRelatedCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/related/{category}", EXISTING_CATEGORY_NAME_IN_USE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void testGetRelatedCategoriesNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/related/{category}", RANDOM_TEST_STRING)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
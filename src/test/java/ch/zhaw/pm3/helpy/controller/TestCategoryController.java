package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.constant.Profiles;
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
class TestCategoryController {

    private static final String REQUEST_MAPPING = "/api/category";
    private static final String EXISTING_CATEGORY_NAME_IN_USE = "Sprache";
    private static final String EXISTING_CATEGORY_NAME_NOT_IN_USE = "Divers";
    private static final String RANDOM_TEST_STRING = "test";

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(categoryController);
    }

    @Test
    void testGetCategoryByName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{category}", EXISTING_CATEGORY_NAME_IN_USE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(EXISTING_CATEGORY_NAME_IN_USE));
    }

    @Test
    void testCreateCategory() throws Exception {
        Category testCat = new Category(RANDOM_TEST_STRING);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(asJsonString(testCat))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(RANDOM_TEST_STRING));
    }

    @Test
    void testGetCategories() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").isNotEmpty());
    }

    @Test
    void testUpdateCategory() throws Exception {
        Category testCat = new Category(EXISTING_CATEGORY_NAME_IN_USE);
        testCat.setDescription(RANDOM_TEST_STRING);
        this.mockMvc.perform(MockMvcRequestBuilders
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
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update")
                .content(asJsonString(testCat))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategory() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{category}", EXISTING_CATEGORY_NAME_NOT_IN_USE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(EXISTING_CATEGORY_NAME_NOT_IN_USE));
    }

    @Test
    void testDeleteCategoryNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{category}", RANDOM_TEST_STRING)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetRelatedCategories() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/related/{category}", EXISTING_CATEGORY_NAME_IN_USE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void testGetRelatedCategoriesNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
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
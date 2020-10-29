package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.constant.Profiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(Profiles.NO_AUTH)
class TestUserController {

    private static final String REQUEST_MAPPING = "/api/user";
    private static final String EXISTING_USER_EMAIL = "rlavigne0@virginia.edu";
    private static final String NONEXISTENT_USER_EMAIL = "sampleMail@user.com";
    private static final String NONEXISTENT_USER_JSON_STRING = "{\"type\":\"Helper\",\"firstname\":\"Carl\",\"lastname\":\"Lubojanski\",\"email\":\"sampleMail@user.com\",\"age\":23,\"sex\":\"M\",\"plz\":8180,\"biographie\":\"Student at ZHAW\",\"password\":\"1234567890\",\"permission\":\"USER\",\"status\":\"ACTIVE\",\"birthdate\":\"2005-03-20\",\"ratings\":[1],\"categories\":[],\"tags\":[]}";
    private static final String NONEXISTENT_USER_FIRSTNAME = "Carl";
    private static final String RANDOM_TEST_STRING = "test";

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(userController);
    }

    @Test
    void testGetUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(EXISTING_USER_EMAIL));
    }

    @Test
    void testGetUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", NONEXISTENT_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].email").isNotEmpty());
    }

    @Test
    void testAddUser() throws Exception {
        System.out.println(NONEXISTENT_USER_JSON_STRING);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(NONEXISTENT_USER_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(NONEXISTENT_USER_EMAIL));
    }

    @Test
    void testRemoveUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{username}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRemoveUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "remove/{username}", NONEXISTENT_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUser() throws Exception {
        String updateString = NONEXISTENT_USER_JSON_STRING;
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(updateString)
                .contentType(MediaType.APPLICATION_JSON));

        updateString = updateString.replace(NONEXISTENT_USER_FIRSTNAME, RANDOM_TEST_STRING);

        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update/{id}", NONEXISTENT_USER_EMAIL)
                .content(updateString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname")
                        .value(RANDOM_TEST_STRING));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update/{id}", NONEXISTENT_USER_EMAIL)
                .content(NONEXISTENT_USER_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

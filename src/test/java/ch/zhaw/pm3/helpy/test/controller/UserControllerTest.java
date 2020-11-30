package ch.zhaw.pm3.helpy.test.controller;

import ch.zhaw.pm3.helpy.config.Profiles;
import ch.zhaw.pm3.helpy.controller.UserController;
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
class UserControllerTest {

    static final String REQUEST_MAPPING = "/api/user";
    static final String EXISTING_USER_EMAIL = "hawkeye@email.com";
    static final String NONEXISTENT_USER_EMAIL = "sampleMail@user.com";
    static final String NONEXISTENT_USER_JSON_STRING = "{\"firstname\":\"Carl\",\"lastname\":\"Lubojanski\",\"email\":\"sampleMail@user.com\",\"age\":23,\"sex\":\"M\",\"plz\":8180,\"biographie\":\"Student at ZHAW\",\"password\":\"1234567890\",\"permission\":\"USER\",\"status\":\"ACTIVE\",\"birthdate\":\"2005-03-20\",\"availableWeekDays\":[],\"wantsToHelpActive\":true,\"ratings\":[1],\"categories\":[],\"tags\":[]}";
    static final String NONEXISTENT_USER_FIRSTNAME = "Carl";
    static final String RANDOM_TEST_STRING = "test";

    @Autowired
    UserController userController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(userController);
    }

    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(EXISTING_USER_EMAIL));
    }

    @Test
    void testGetUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", NONEXISTENT_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].email").isNotEmpty());
    }

    @Test
    void testAddUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(NONEXISTENT_USER_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(NONEXISTENT_USER_EMAIL));
    }

    @Test
    void testRemoveUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{username}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRemoveUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "remove/{username}", NONEXISTENT_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUser() throws Exception {
        String updateString = NONEXISTENT_USER_JSON_STRING;
        mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(updateString)
                .contentType(MediaType.APPLICATION_JSON));

        updateString = updateString.replace(NONEXISTENT_USER_FIRSTNAME, RANDOM_TEST_STRING);

        mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update/{id}", NONEXISTENT_USER_EMAIL)
                .content(updateString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname")
                        .value(RANDOM_TEST_STRING));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update/{id}", NONEXISTENT_USER_EMAIL)
                .content(NONEXISTENT_USER_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

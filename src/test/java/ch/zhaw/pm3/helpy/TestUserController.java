package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.controller.UserController;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

    private static final String REQUEST_MAPPING = "/api/user";
    private static final String EXISTING_USER_EMAIL = "rlavigne0@virginia.edu";
    private static final String NONEXISTENT_USER_EMAIL = "sampleMail@user.com";
    private static final String NONEXISTENT_USER_JSON_STRING = "{\"type\":\"Helper\",\"firstname\":\"Carl\",\"lastname\":\"Lubojanski\",\"email\":\"sampleMail@user.com\",\"age\":23,\"sex\":\"M\",\"plz\":8180,\"biographie\":\"Student at ZHAW\",\"password\":\"1234567890\",\"permission\":\"USER\",\"status\":\"ACTIVE\",\"birthdate\":\"2005-03-20\",\"ratings\":[1],\"categories\":[],\"tags\":[]}";


    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertNotNull(userController);
    }

    @Test
    public void testGetUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(EXISTING_USER_EMAIL));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/{username}", NONEXISTENT_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].email").isNotEmpty());
    }

    @Test
    public void testAddUser() throws Exception {
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
    public void testRemoveUser() throws Exception {
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
    public void testRemoveUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "remove/{username}", NONEXISTENT_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser() throws Exception {
        //todo HttpRequestMethodNotSupportedException
        User.UserBuilder builder = new User.UserBuilder();
        builder.setEmail(EXISTING_USER_EMAIL);
        User user = new User(builder);
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(EXISTING_USER_EMAIL));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        //todo: HttpRequestMethodNotSupportedException
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update", NONEXISTENT_USER_EMAIL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

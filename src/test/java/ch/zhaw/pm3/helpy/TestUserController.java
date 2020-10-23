package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.controller.UserController;
import ch.zhaw.pm3.helpy.model.Category;
import ch.zhaw.pm3.helpy.model.Helper;
import ch.zhaw.pm3.helpy.model.Helpseeker;
import ch.zhaw.pm3.helpy.model.User;
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
        User.UserBuilder builder = new User.UserBuilder();
        builder.setEmail(NONEXISTENT_USER_EMAIL);
        User user = new User(builder);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(NONEXISTENT_USER_EMAIL));
    }

    @Test
    public void testAddHelpseeker() throws Exception {
        //todo MethodArgumentNotValidException
        User.UserBuilder builder = new User.UserBuilder();
        builder.setEmail(NONEXISTENT_USER_EMAIL);
        Helpseeker helpseeker = new Helpseeker(builder);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/addhelpseeker")
                .content(asJsonString(helpseeker))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(NONEXISTENT_USER_EMAIL));
    }

    @Test
    public void testAddHelper() throws Exception {
        //todo MethodArgumentNotValidException
        User.UserBuilder builder = new User.UserBuilder();
        builder.setEmail(NONEXISTENT_USER_EMAIL);
        Helper helper = new Helper(builder);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/addhelper")
                .content(asJsonString(helper))
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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value(EXISTING_USER_EMAIL));
        //todo: Resolve delete error -> maybe (fetch = FetchType.EAGER) as with Category and Tags
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

    @Test
    public void testGetByPlz() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/plz/{plz}", 6784)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
                //todo check response

    }

    @Test
    public void testGetByStatus() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/status/{status}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
                //todo check response

    }

    @Test
    public void testGetByRating() throws Exception {
        fail("Rating not yet implemented in DB");
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/rating/{rating}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
                //todo check response
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

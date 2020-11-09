package ch.zhaw.pm3.helpy.security;

import ch.zhaw.pm3.helpy.constant.Profiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(Profiles.BASIC_AUTH)
public class TestBasicAuth {

    private static final String REQUEST_MAPPING = "/api/user";
    private static final String NONEXISTENT_USER_EMAIL = "sampleMail@user.com";
    private static final String NONEXISTENT_USER_JSON_STRING = "{\"type\":\"Helper\",\"firstname\":\"Carl\",\"lastname\":\"Lubojanski\",\"email\":\"sampleMail@user.com\",\"age\":23,\"sex\":\"M\",\"plz\":8180,\"biographie\":\"Student at ZHAW\",\"password\":\"1234567890\",\"permission\":\"USER\",\"status\":\"ACTIVE\",\"birthdate\":\"2005-03-20\",\"ratings\":[1],\"categories\":[],\"tags\":[]}";

    private static final String EXISTING_USER_EMAIL = "leandro@email.com";
    private static final String EXISTING_USER_PASSWORD = "m1y9zQYlz";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenAuthRequestOnPrivateService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .post(REQUEST_MAPPING + "/add")
                            .content(NONEXISTENT_USER_JSON_STRING)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                                   .value(NONEXISTENT_USER_EMAIL));
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                                     .get(REQUEST_MAPPING + "/all")
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = EXISTING_USER_EMAIL, password = EXISTING_USER_PASSWORD)
    void testRemoveWithLoggedInUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                                     .delete(REQUEST_MAPPING + "/remove/{username}", EXISTING_USER_EMAIL)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                                     .get(REQUEST_MAPPING + "/{username}", EXISTING_USER_EMAIL)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

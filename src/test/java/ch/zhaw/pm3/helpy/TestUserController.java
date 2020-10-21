package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

    private static final String REQUEST_MAPPING = "/api/user";

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetUser() throws Exception {

    }

    @Test
    public void testGetUserNotFound() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testAddUser() throws Exception {

    }

    @Test
    public void testAddHelpseeker() throws Exception {

    }

    @Test
    public void testAddHelper() throws Exception {

    }

    @Test
    public void testRemoveUser() throws Exception {

    }

    @Test
    public void testRemoveUserNotFound() throws Exception {

    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testUpdateUserNotFound() throws Exception {

    }

    @Test
    public void testGetByPlz() throws Exception {

    }

    @Test
    public void testGetByStatus() throws Exception {

    }

    @Test
    public void testGetByAge() throws Exception {

    }

    @Test
    public void testGetByRating() throws Exception {

    }
}

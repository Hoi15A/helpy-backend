package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.controller.JobController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestJobController {

    private static final String REQUEST_MAPPING = "/api/job";

    @Autowired
    private JobController jobController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetJobs() throws Exception {

    }

    @Test
    public void testCreateJob() throws Exception {

    }

    @Test
    public void testRemoveJob() throws Exception {

    }

    @Test
    public void testRemoveJobNotFound() throws Exception {

    }

    @Test
    public void testUpdateJob() throws Exception {

    }

    @Test
    public void testUpdateJobNotFound() throws Exception {

    }

    @Test
    public void testGetJobById() throws Exception {

    }

    @Test
    public void testGetJobByIdNotFound() throws Exception {

    }

    @Test
    public void testGetJobsByStatus() throws Exception {

    }

    @Test
    public void testGetJobsByAuthor() throws Exception {

    }

    @Test
    public void testGetJobsByCategory() throws Exception {

    }

    @Test
    public void testGetJobsByCategories() throws Exception {

    }

    @Test
    public void testGetJobsByTags() throws Exception {

    }

    @Test
    public void testGetJobsByDate() throws Exception {

    }



}

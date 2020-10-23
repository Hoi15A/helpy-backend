package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.controller.JobController;
import ch.zhaw.pm3.helpy.model.Category;
import ch.zhaw.pm3.helpy.model.Helpseeker;
import ch.zhaw.pm3.helpy.model.Job;
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
public class TestJobController {

    //todo Tests for Tag not found

    private static final String REQUEST_MAPPING = "/api/job";
    private static final int EXISTING_JOB_ID = 1;
    private static final int NONEXISTENT_JOB_ID = -1;

    @Autowired
    private JobController jobController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertNotNull(jobController);
    }

    @Test
    public void testGetJobs() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    public void testCreateJob() throws Exception {
        //todo FAILURE: HttpMediaTypeNotSupportedException
        Job job = new Job("test", "test", new Helpseeker());
        job.setId(51);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(asJsonString(job))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(51));
    }

    @Test
    public void testRemoveJob() throws Exception {
        //todo: FAILURE: Referential integrity constraint violation
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{id}", EXISTING_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(EXISTING_JOB_ID));
    }

    @Test
    public void testRemoveJobNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{category}", NONEXISTENT_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateJob() throws Exception {
        //todo FAILURE: HttpMediaTypeNotSupportedException
        User.UserBuilder builder = new User.UserBuilder();
        Helpseeker author = new Helpseeker(builder);
        Job job = new Job("test", "test", author);
        job.setId(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update")
                .content(asJsonString(job))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(1));
    }

    @Test
    public void testUpdateJobNotFound() throws Exception {
        //todo FAILURE: HttpMediaTypeNotSupportedException
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update", NONEXISTENT_JOB_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetJobById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/id/{id}", EXISTING_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(EXISTING_JOB_ID));
    }

    @Test
    public void testGetJobByIdNotFound() throws Exception {
        //todo
    }

    @Test
    public void testGetJobsByStatus() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/status/{status}", 0)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
        //todo check response
    }

    @Test
    public void testGetJobsByAuthor() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/author/{author}", "clynett1@furl.net")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
        //todo check response
    }

    @Test
    public void testGetJobsByCategory() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/category/{category}", "Sprache")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
        //todo check response
    }

    @Test
    public void testGetJobsByCategories() throws Exception {
        //todo
    }

    @Test
    public void testGetJobsByTag() throws Exception {
        fail("Tag not yet linked to Job in DB");
        //todo
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "tag/{tag}", "cohesive")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
        //todo check response
    }

    @Test
    public void testGetJobsByTags() throws Exception {
        //todo
    }

    @Test
    public void testGetJobsByDate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "date/{date}", "2020-06-16")
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

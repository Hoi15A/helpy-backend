package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestJobController {
    private static final String REQUEST_MAPPING = "/api/job";
    private static final int EXISTING_JOB_ID = 11;
    private static final int NONEXISTENT_JOB_ID = -1;
    private static final int NEXT_AVAILABLE_JOB_ID = 105;
    private static final String NEW_USER_AS_JSON_STRING = "{\"author\":{\"email\":\"ahmed_miri@gmx.net\",\"type\":\"Helpseeker\"},\"title\":\"test12345\",\"categories\":[{\"name\":\"Physisch\",\"listOfRelated\":[],\"description\":\"magnis dis parturient montes nascetur\"},{\"name\":\"Geistig\",\"listOfRelated\":[],\"description\":\"lectus suspendisse potenti in eleifend\"},{\"name\":\"ÖV\",\"listOfRelated\":[],\"description\":\"nec euismod scelerisque quam turpis adipiscing lorem vitae mattis\"}],\"tags\":[],\"description\":\"aaaa\"}";
    private static final String EXISTING_USER_ID_UPDATED_INFO_AS_JASON_STRING = "{\"id\":1,\"title\":\"test\",\"description\":\"sed justo pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est\",\"author\":{\"type\":\"Helpseeker\",\"email\":\"ahmed_miri@gmx.net\",\"firstname\":\"Ahmed\",\"lastname\":\"Miri\",\"sex\":\"M\",\"plz\":8400,\"birthdate\":\"2003-01-05\",\"biographie\":\"Ich heisse Ahmed und bin 17 Jahre alt und bin seit 2015 in der Schweiz und komme aus Afghanistan. Ich wohne in Winterthur und gehe im Moment in die 10. Klasse. Ich schaue gerne Fussball und spiele beim SC Veltheim in der U19 2. Mannschaft. Ich habe Probleme mit Schreiben und Lesen von wichtigen Papieren in der Schweiz und verstehe sie nicht alle.\",\"status\":\"INACTIVE\",\"permission\":\"USER\"},\"created\":\"2020-06-16\",\"status\":\"OPEN\",\"matchedHelper\":null,\"categories\":[{\"name\":\"Sprache\",\"listOfRelated\":[],\"description\":\"eget tincidunt eget tempus vel pede morbi porttitor lorem id\"},{\"name\":\"Schule\",\"listOfRelated\":[],\"description\":\"aenean fermentum donec ut mauris eget\"},{\"name\":\"Physisch\",\"listOfRelated\":[],\"description\":\"magnis dis parturient montes nascetur\"},{\"name\":\"Behörden\",\"listOfRelated\":[],\"description\":\"quisque arcu libero rutrum ac lobortis vel\"},{\"name\":\"Geistig\",\"listOfRelated\":[],\"description\":\"lectus suspendisse potenti in eleifend\"}],\"tags\":[{\"name\":\"encryption\",\"listOfRelated\":[],\"description\":\"volutpat quam pede lobortis ligula sit amet eleifend\"}]}";
    private static final String RANDOM_TEST_STRING = "test";
    private static final String NONEXISTENT_USER_ID_AS_JSON_STRING = "{\"id\":-1,\"title\":\"update\",\"description\":\"sed justo pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est\",\"author\":{\"type\":\"Helpseeker\",\"email\":\"ahmed_miri@gmx.net\",\"firstname\":\"Ahmed\",\"lastname\":\"Miri\",\"sex\":\"M\",\"plz\":8400,\"birthdate\":\"2003-01-05\",\"biographie\":\"Ich heisse Ahmed und bin 17 Jahre alt und bin seit 2015 in der Schweiz und komme aus Afghanistan. Ich wohne in Winterthur und gehe im Moment in die 10. Klasse. Ich schaue gerne Fussball und spiele beim SC Veltheim in der U19 2. Mannschaft. Ich habe Probleme mit Schreiben und Lesen von wichtigen Papieren in der Schweiz und verstehe sie nicht alle.\",\"status\":\"INACTIVE\",\"permission\":\"USER\"},\"created\":\"2020-06-16\",\"status\":\"OPEN\",\"matchedHelper\":null,\"categories\":[{\"name\":\"Sprache\",\"listOfRelated\":[],\"description\":\"eget tincidunt eget tempus vel pede morbi porttitor lorem id\"},{\"name\":\"Schule\",\"listOfRelated\":[],\"description\":\"aenean fermentum donec ut mauris eget\"},{\"name\":\"Physisch\",\"listOfRelated\":[],\"description\":\"magnis dis parturient montes nascetur\"},{\"name\":\"Behörden\",\"listOfRelated\":[],\"description\":\"quisque arcu libero rutrum ac lobortis vel\"},{\"name\":\"Geistig\",\"listOfRelated\":[],\"description\":\"lectus suspendisse potenti in eleifend\"}],\"tags\":[{\"name\":\"encryption\",\"listOfRelated\":[],\"description\":\"volutpat quam pede lobortis ligula sit amet eleifend\"}]}";
    private static final String EXISTING_USER_EMAIL = "mj@email.com";
    private static final String EXISTING_CATEGORY_TITLE_1 = "Sprache";
    private static final String EXISTING_CATEGORY_TITLE_2 = "Schule";
    private static final String TWO_EXISTING_CATEGORIES_AS_JSON_STRING = "[{\"name\":\"Sprache\",\"listOfRelated\":[],\"description\":\"eget tincidunt eget tempus vel pede morbi porttitor lorem id\"},{\"name\":\"Schule\",\"listOfRelated\":[],\"description\":\"aenean fermentum donec ut mauris eget\"}]";
    private static final String EXISTING_TAG_TITLE_1 = "cohesive";
    private static final String EXISTING_TAG_TITLE_2 = "conglomeration";
    private static final String TWO_EXISTING_TAGS_AS_JSON_STRING = "[{\"name\":\"conglomeration\",\"listOfRelated\":[],\"description\":\"facilisi cras non velit nec nisi vulputate\"},{\"name\":\"cohesive\",\"listOfRelated\":[],\"description\":\"turpis enim blandit mi in porttitor pede\"}]";
    private static final String EXISTING_JOB_DATE = "2020-01-01";

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
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(NEW_USER_AS_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(NEXT_AVAILABLE_JOB_ID));
    }

    @Test
    public void testRemoveJob() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{id}", EXISTING_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/id/{id}", EXISTING_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update")
                .content(EXISTING_USER_ID_UPDATED_INFO_AS_JASON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value(RANDOM_TEST_STRING));
    }

    @Test
    public void testUpdateJobNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update", NONEXISTENT_JOB_ID)
                .content(NONEXISTENT_USER_ID_AS_JSON_STRING)
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
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/id/{id}" , NONEXISTENT_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetJobsByStatus() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/status/{status}", JobStatus.OPEN)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].status",
                        Matchers.hasItem(JobStatus.OPEN.toString())));
    }

    @Test
    public void testGetJobsByAuthor() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/author/{author}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].author.email",
                        Matchers.hasItem(EXISTING_USER_EMAIL)));
    }

    @Test
    public void testGetJobsByCategory() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/category/{category}", EXISTING_CATEGORY_TITLE_1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].categories.[*].name",
                        Matchers.hasItem(EXISTING_CATEGORY_TITLE_1)));
    }

    @Test
    public void testGetJobsByCategories() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/categories")
                .content(TWO_EXISTING_CATEGORIES_AS_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].categories.[*].name",
                        Matchers.hasItems(EXISTING_CATEGORY_TITLE_1, EXISTING_CATEGORY_TITLE_2)));
    }

    @Test
    public void testGetJobsByTag() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/tag/{tag}", EXISTING_TAG_TITLE_1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].tags.[*].name",
                        Matchers.hasItem(EXISTING_TAG_TITLE_1)));
    }

    @Test
    public void testGetJobsByTags() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/tags")
                .content(TWO_EXISTING_TAGS_AS_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].tags.[*].name",
                        Matchers.hasItems(EXISTING_TAG_TITLE_1, EXISTING_TAG_TITLE_2)));
    }

    @Test
    public void testGetJobsByDate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/date/{date}", EXISTING_JOB_DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].created",
                        Matchers.hasItem(EXISTING_JOB_DATE)));
    }
}

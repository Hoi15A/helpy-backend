package ch.zhaw.pm3.helpy.test.controller;

import ch.zhaw.pm3.helpy.model.job.JobStatus;
import ch.zhaw.pm3.helpy.config.Profiles;
import ch.zhaw.pm3.helpy.controller.JobController;
import org.hamcrest.Matchers;
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
class JobControllerTest {
    static final String REQUEST_MAPPING = "/api/job";
    static final int EXISTING_JOB_ID = 100;
    static final int EXISTING_JOB_ID_UPDATE = 101;
    static final int EXISTING_JOB_ID_DELETE = 102;
    static final int NONEXISTENT_JOB_ID = -1;
    static final int NEXT_AVAILABLE_JOB_ID = 105;
    static final String NEW_JOB_AS_JSON_STRING = "{\"dueDate\":\"2021-01-01\",\"author\":{\"email\":\"mj@email.com\"},\"title\":\"test12345\",\"categories\":[{\"name\":\"Physisch\",\"listOfRelated\":[],\"description\":\"magnis dis parturient montes nascetur\"},{\"name\":\"ÖV\",\"listOfRelated\":[],\"description\":\"nec euismod scelerisque quam turpis adipiscing lorem vitae mattis\"}],\"tags\":[],\"description\":\"aaaa\"}";
    static final String EXISTING_USER_ID_UPDATED_INFO_AS_JASON_STRING = "{\"dueDate\":\"2021-01-01\",\"title\":\"test\",\"description\":\"sed justo pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est\",\"author\":{\"email\":\"ahmed_miri@gmx.net\",\"firstname\":\"Ahmed\",\"lastname\":\"Miri\",\"sex\":\"M\",\"plz\":8400,\"birthdate\":\"2003-01-05\",\"biographie\":\"Ich heisse Ahmed und bin 17 Jahre alt und bin seit 2015 in der Schweiz und komme aus Afghanistan. Ich wohne in Winterthur und gehe im Moment in die 10. Klasse. Ich schaue gerne Fussball und spiele beim SC Veltheim in der U19 2. Mannschaft. Ich habe Probleme mit Schreiben und Lesen von wichtigen Papieren in der Schweiz und verstehe sie nicht alle.\",\"status\":\"INACTIVE\",\"permission\":\"USER\"},\"created\":\"2020-06-16\",\"status\":\"OPEN\",\"matchedHelper\":null,\"categories\":[{\"name\":\"Sprache\",\"listOfRelated\":[],\"description\":\"eget tincidunt eget tempus vel pede morbi porttitor lorem id\"},{\"name\":\"Schule\",\"listOfRelated\":[],\"description\":\"aenean fermentum donec ut mauris eget\"},{\"name\":\"Physisch\",\"listOfRelated\":[],\"description\":\"magnis dis parturient montes nascetur\"},{\"name\":\"Behörden\",\"listOfRelated\":[],\"description\":\"quisque arcu libero rutrum ac lobortis vel\"}],\"tags\":[{\"name\":\"Betreibung\",\"listOfRelated\":[],\"description\":\"volutpat quam pede lobortis ligula sit amet eleifend\"}]}";
    static final String RANDOM_TEST_STRING = "test";
    static final String NONEXISTENT_JOB_ID_AS_JSON_STRING = "{\"dueDate\":\"2021-01-01\",\"title\":\"update\",\"description\":\"void\",\"author\":{\"email\":\"ahmed_miri@gmx.net\",\"firstname\":\"Ahmed\",\"lastname\":\"Miri\",\"sex\":\"M\",\"plz\":8400,\"birthdate\":\"2003-01-05\",\"biographie\":\"Ich heisse Ahmed und bin 17 Jahre alt und bin seit 2015 in der Schweiz und komme aus Afghanistan. Ich wohne in Winterthur und gehe im Moment in die 10. Klasse. Ich schaue gerne Fussball und spiele beim SC Veltheim in der U19 2. Mannschaft. Ich habe Probleme mit Schreiben und Lesen von wichtigen Papieren in der Schweiz und verstehe sie nicht alle.\",\"status\":\"INACTIVE\",\"permission\":\"USER\"},\"created\":\"2020-06-16\",\"status\":\"OPEN\",\"matchedHelper\":null,\"categories\":[{\"name\":\"Sprache\",\"listOfRelated\":[],\"description\":\"eget tincidunt eget tempus vel pede morbi porttitor lorem id\"},{\"name\":\"Schule\",\"listOfRelated\":[],\"description\":\"aenean fermentum donec ut mauris eget\"},{\"name\":\"Physisch\",\"listOfRelated\":[],\"description\":\"magnis dis parturient montes nascetur\"},{\"name\":\"Behörden\",\"listOfRelated\":[],\"description\":\"quisque arcu libero rutrum ac lobortis vel\"}],\"tags\":[{\"name\":\"encryption\",\"listOfRelated\":[],\"description\":\"volutpat quam pede lobortis ligula sit amet eleifend\"}]}";
    static final String EXISTING_USER_EMAIL = "mj@email.com";
    static final String EXISTING_CATEGORY_TITLE_1 = "Sprache";
    static final String EXISTING_CATEGORY_TITLE_2 = "Schule";
    static final String TWO_EXISTING_CATEGORIES_AS_JSON_STRING = "[{\"name\":\"Sprache\",\"listOfRelated\":[],\"description\":\"eget tincidunt eget tempus vel pede morbi porttitor lorem id\"},{\"name\":\"Schule\",\"listOfRelated\":[],\"description\":\"aenean fermentum donec ut mauris eget\"}]";
    static final String EXISTING_TAG_TITLE_1 = "Abonnement";
    static final String EXISTING_TAG_TITLE_2 = "Kino";
    static final String TWO_EXISTING_TAGS_AS_JSON_STRING = "[{\"name\":\"Abonnement\",\"listOfRelated\":[],\"description\":\"facilisi cras non velit nec nisi vulputate\"},{\"name\":\"Kino\",\"listOfRelated\":[],\"description\":\"turpis enim blandit mi in porttitor pede\"}]";
    static final String EXISTING_JOB_DATE = "2020-01-01";

    @Autowired
    JobController jobController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(jobController);
    }

    @Test
    void testGetJobs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void testCreateJob() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/add")
                .content(NEW_JOB_AS_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(NEXT_AVAILABLE_JOB_ID));
    }

    @Test
    void testRemoveJob() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{id}", EXISTING_JOB_ID_DELETE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/id/{id}", EXISTING_JOB_ID_DELETE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRemoveJobNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(REQUEST_MAPPING + "/remove/{category}", NONEXISTENT_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateJob() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update/{id}", EXISTING_JOB_ID_UPDATE)
                .content(EXISTING_USER_ID_UPDATED_INFO_AS_JASON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value(RANDOM_TEST_STRING));
    }

    @Test
    void testUpdateJobNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING + "/update/{id}", NONEXISTENT_JOB_ID)
                .content(NONEXISTENT_JOB_ID_AS_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetJobById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/id/{id}", EXISTING_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(EXISTING_JOB_ID));
    }

    @Test
    void testGetJobByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/id/{id}" , NONEXISTENT_JOB_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetJobsByStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/status/{status}", JobStatus.OPEN)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].status",
                        Matchers.hasItem(JobStatus.OPEN.toString())));
    }

    @Test
    void testGetJobsByAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/author/{author}", EXISTING_USER_EMAIL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].author.email",
                        Matchers.hasItem(EXISTING_USER_EMAIL)));
    }

    @Test
    void testGetJobsByCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/category/{category}", EXISTING_CATEGORY_TITLE_1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].categories.[*].name",
                        Matchers.hasItem(EXISTING_CATEGORY_TITLE_1)));
    }

    @Test
    void testGetJobsByCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/categories")
                .content(TWO_EXISTING_CATEGORIES_AS_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].categories.[*].name",
                        Matchers.anyOf(Matchers.hasItem(EXISTING_CATEGORY_TITLE_1), Matchers.hasItem(EXISTING_CATEGORY_TITLE_2))));
    }

    @Test
    void testGetJobsByTag() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/tag/{tag}", EXISTING_TAG_TITLE_1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].tags.[*].name",
                        Matchers.hasItem(EXISTING_TAG_TITLE_1)));
    }

    @Test
    void testGetJobsByTags() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(REQUEST_MAPPING + "/tags")
                .content(TWO_EXISTING_TAGS_AS_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].tags.[*].name",
                        Matchers.anyOf(Matchers.hasItem(EXISTING_TAG_TITLE_1), Matchers.hasItem(EXISTING_TAG_TITLE_2))));
    }

    @Test
    void testGetJobsByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REQUEST_MAPPING + "/date/{date}", EXISTING_JOB_DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].created",
                        Matchers.hasItem(EXISTING_JOB_DATE)));
    }
}

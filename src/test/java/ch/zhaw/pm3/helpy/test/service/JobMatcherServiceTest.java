package ch.zhaw.pm3.helpy.test.service;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import ch.zhaw.pm3.helpy.service.JobMatcherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobMatcherServiceTest {

    static final int AUTHOR_PLZ = 8406;

    @Mock User helpseeker;
    @Mock Job job;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobMatcherService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
        assertNotNull(service);
    }

    @Test
    void testMatch() {
        when(job.getAuthor()).thenReturn(helpseeker);
        when(job.getAuthor().getPlz()).thenReturn(AUTHOR_PLZ);
        when(job.getCategories()).thenReturn(getJobCategories());
        when(job.getTags()).thenReturn(getJobTags());

        List<User> result = service.getPotentialHelpersForJob(job);
        assertIterableEquals(getExpectedResultList(), result);
    }

    private List<User> getExpectedResultList() {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(userRepository.findById("leandro@email.com").get());
        expectedList.add(userRepository.findById("hawkeye@email.com").get());
        expectedList.add(userRepository.findById("spidey@email.com").get());
        return expectedList;
    }

    private Set<Category> getJobCategories() {
        Set<Category> jobCategories = new HashSet<>();
        jobCategories.add(new Category("Administrativ"));
        jobCategories.add(new Category("Sprache"));
        jobCategories.add(new Category("ÖV"));
        return jobCategories;
    }

    private Set<Tag> getJobTags() {
        Set<Tag> jobTags = new HashSet<>();
        jobTags.add(new Tag("Swisspass"));
        jobTags.add(new Tag("Abonnement"));
        jobTags.add(new Tag("Winterthur"));
        jobTags.add(new Tag("Zürich"));
        return jobTags;
    }

}

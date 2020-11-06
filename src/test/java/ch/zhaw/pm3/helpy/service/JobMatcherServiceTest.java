package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private static final int AUTHOR_PLZ = 8406;

    private Helpseeker helpseeker;
    private Job job;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobMatcherService service;

    @BeforeEach
    void setUp() {
        helpseeker = mock(Helpseeker.class);
        job = mock(Job.class);
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

        List<Helper> result = service.getPotentialHelpersForJob(job);
        assertIterableEquals(getExpectedResultList(), result);
    }

    private List<Helper> getExpectedResultList() {
        List<Helper> expectedList = new ArrayList<>();
        expectedList.add((Helper) userRepository.findUserByEmail("leandro@email.com"));
        expectedList.add((Helper) userRepository.findUserByEmail("hawkeye@email.com"));
        expectedList.add((Helper) userRepository.findUserByEmail("spidey@email.com"));
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

package ch.zhaw.pm3.helpy.matcher;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobMatcherTest {

    @Autowired
    private UserRepository userRepository;

    private Helpseeker helpseeker;
    private Job job;

    @Autowired
    private JobMatcher matcher;

    @BeforeEach
    public void ListUp() {
        helpseeker = mock(Helpseeker.class);
        job = mock(Job.class);
        matcher.setJob(job);
    }

    @Test
    public void contextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    public void testMatch() {
        when(job.getAuthor()).thenReturn(helpseeker);
        when(job.getAuthor().getPlz()).thenReturn(8406);
        when(job.getCategories()).thenReturn(getJobCategories());
        when(job.getTags()).thenReturn(getJobTags());

        List<Helper> result = matcher.getPotentialHelper();
        assertIterableEquals(getExpectedResultList(), result);
    }

    private List<Helper> getExpectedResultList() {
        List<Helper> expectedList = new ArrayList<>();
        expectedList.add((Helper) userRepository.findUserByName("leandro@email.com"));
        expectedList.add((Helper) userRepository.findUserByName("hawkeye@email.com"));
        expectedList.add((Helper) userRepository.findUserByName("spidey@email.com"));
        return expectedList;
    }

    private List<Category> getJobCategories() {
        List<Category> jobCategories = new ArrayList<>();
        jobCategories.add(new Category("Administrativ"));
        jobCategories.add(new Category("Sprache"));
        jobCategories.add(new Category("ÖV"));
        return jobCategories;
    }

    private List<Tag> getJobTags() {
        List<Tag> jobTags = new ArrayList<>();
        jobTags.add(new Tag("Swisspass"));
        jobTags.add(new Tag("Abonnement"));
        jobTags.add(new Tag("Winterthur"));
        jobTags.add(new Tag("Zürich"));
        return jobTags;
    }

}

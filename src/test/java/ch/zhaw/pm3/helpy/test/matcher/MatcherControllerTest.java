package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.MatcherController;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.LocationRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class MatcherControllerTest {

    private MatcherController matcherController;
    private static final String TEST_DATE1 = "2020-12-07";
    private static final String TEST_DATE2 = "2020-12-11";

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Mock
    Job job;

    @Mock
    User user;

    @BeforeEach
    void beforeSetup() {
        MockitoAnnotations.initMocks(this);
        matcherController = new MatcherController();
        matcherController.setUserRepository(userRepository);
        matcherController.setLocationRepository(locationRepository);
        when(job.getAuthor()).thenReturn(user);
    }

    @Test
    void testHelperIsHelpseeker() {
        when(job.getDueDate()).thenReturn(LocalDate.parse(TEST_DATE1));
        when(user.getPlz()).thenReturn(8409);
        when(user.getEmail()).thenReturn("hulk@email.com");
        Collection<User> matchedUsers = matcherController.getPotentialMatches(job);
        assertEquals(0, matchedUsers.size());
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    void testCaseBelowMinMatches() {
        when(job.getDueDate()).thenReturn(LocalDate.parse(TEST_DATE1));
        when(user.getPlz()).thenReturn(8409);
        List<User> matchedUsers = new ArrayList<>(matcherController.getPotentialMatches(job));
        assertEquals(1, matchedUsers.size());
        assertEquals("hulk@email.com", matchedUsers.get(0).getEmail());
    }

    @Test
    void testCaseAboveMinMatchesWithTwoOptionalStrategies() {
        when(job.getDueDate()).thenReturn(LocalDate.parse(TEST_DATE2));
        when(user.getPlz()).thenReturn(8409);
        Set<Category> catSet = new HashSet<>();
        catSet.add(new Category("Physisch"));
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("Betreibung"));
        when(job.getCategories()).thenReturn(catSet);
        when(job.getTags()).thenReturn(tagSet);


        List<User> matchedUsers = new ArrayList<>(matcherController.getPotentialMatches(job));
        assertEquals(5, matchedUsers.size());
    }

    @Test
    void testCaseAboveMinMatchesWithOneOptionalStrategies() {
        when(job.getDueDate()).thenReturn(LocalDate.parse(TEST_DATE2));
        when(user.getPlz()).thenReturn(8409);
        Set<Category> set = new HashSet<>();
        set.add(new Category("Physisch"));
        when(job.getCategories()).thenReturn(set);

        List<User> matchedUsers = new ArrayList<>(matcherController.getPotentialMatches(job));
        assertEquals(5, matchedUsers.size());
    }
}

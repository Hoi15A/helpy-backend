package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.MatcherController;
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
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MatcherControllerTest {

    private MatcherController matcherController;
    private static final String TEST_DATE = "2020-12-07";

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
        when(user.getPlz()).thenReturn(8409);
        when(job.getDueDate()).thenReturn(LocalDate.parse(TEST_DATE));
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    void testCaseBelowMinMatches() {
        Collection<User> matchedUsers = matcherController.getPotentialMatches(job);
        for(User user : matchedUsers){
            System.out.println(user.getEmail());
        }
    }

    @Test
    void testCaseAboveMinMatches() {

    }
}

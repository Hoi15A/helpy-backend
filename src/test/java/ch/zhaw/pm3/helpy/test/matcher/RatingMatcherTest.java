package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.MatcherStrategy;
import ch.zhaw.pm3.helpy.matcher.strategy.RatingStrategy;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RatingMatcherTest {

    @Autowired
    UserRepository userRepository;

    @Mock
    Job job;

    MatcherStrategy ratingStrategy = new RatingStrategy();

    @BeforeEach
    void beforeSetup() {
        MockitoAnnotations.initMocks(this);
        ratingStrategy.setUserRepository(userRepository);
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    void testRating() {
        List<User> ratings = new ArrayList<>(ratingStrategy.getPotentialHelpers(job));
        assertNotEquals(0, ratings.size());
        assertEquals("leandro@email.com", ratings.get(0).getEmail());
        assertEquals("spidey@email.com", ratings.get(1).getEmail());
    }
}

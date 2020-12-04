package ch.zhaw.pm3.helpy.test.matcher.filter;

import ch.zhaw.pm3.helpy.matcher.filter.RatingFilter;
import ch.zhaw.pm3.helpy.matcher.filter.Filter;
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
class RatingFilterTest {

    @Autowired
    UserRepository userRepository;

    @Mock
    Job job;

    final Filter ratingFilter = new RatingFilter();

    @BeforeEach
    void beforeSetup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    void testRating() {
        List<User> ratings = new ArrayList<>(ratingFilter.filterPotentialHelpers(job, new ArrayList<>(userRepository.findAllByRating())));
        assertNotEquals(0, ratings.size());
        assertEquals("leandro@email.com", ratings.get(0).getEmail());
        assertEquals("spidey@email.com", ratings.get(1).getEmail());
    }
}

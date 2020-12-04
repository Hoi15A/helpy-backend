package ch.zhaw.pm3.helpy.test.matcher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ch.zhaw.pm3.helpy.matcher.strategy.Strategy;
import ch.zhaw.pm3.helpy.matcher.strategy.WeekdayStrategy;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeekdayMatcherTest {

    private static final String TEST_DATE = "2020-11-28";
    private static Strategy weekdayMatcher;
    private static final Set<String> expectedUsersByEmail = new HashSet<>(Arrays.asList(
            "dallmayr@email.com",
            "spidey@email.com",
            "leandro@email.com",
            "hawkeye@email.com",
            "thor@email.com"
    ));
    private Set<User> expectedUsers;

    @Mock
    Job job;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        weekdayMatcher = new WeekdayStrategy();
        expectedUsers = initExpectedUsers();
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
    }

    @Test
    void testMatch() {
        when(job.getDueDate()).thenReturn(LocalDate.parse(TEST_DATE));
        Set<User> result = new HashSet<>(weekdayMatcher.filterPotentialHelpers(job, new ArrayList<>(userRepository.findAllWithAvailableWeekdays())));
        assertEquals(5, result.size());
        for(User user : result) {
            assertTrue(expectedUsers.contains(user));
        }
    }

    private Set<User> initExpectedUsers() {
        Set<User> expectedUsers = new HashSet<>();
        for(String userEmail : expectedUsersByEmail) {
            expectedUsers.add(userRepository.findById(userEmail).get());
        }
        return expectedUsers;
    }
}

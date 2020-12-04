package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.TagStrategy;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TagStrategyTest {
    @Mock Job job;

    @Autowired
    UserRepository userRepository;

    TagStrategy tagStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tagStrategy = new TagStrategy();
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
        assertNotNull(tagStrategy);
    }

    @Test
    void testTagMatching() {
        when(job.getTags()).thenReturn(getJobTags());
        Collection<User> result = tagStrategy.filterPotentialHelpers(job, new ArrayList<>(userRepository.findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE)));
        assertIterableEquals(getExpectedResultList(), result);
    }

    private Set<Tag> getJobTags() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("Winterthur"));
        tagSet.add(new Tag("Zürich"));
        tagSet.add(new Tag("Abonnement"));
        tagSet.add(new Tag("Swisspass"));
        return tagSet;
    }

    private Set<User> getExpectedResultList() {
        Set<User> expectedResult = new HashSet<>();
        expectedResult.add(userRepository.findById("leandro@email.com").get());
        expectedResult.add(userRepository.findById("spidey@email.com").get());
        expectedResult.add(userRepository.findById("hawkeye@email.com").get());
        return expectedResult;
    }
}

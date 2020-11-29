package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.matcher.strategy.CategoryMatcher;
import ch.zhaw.pm3.helpy.matcher.strategy.TagMatcher;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TagMatcherTest {
    @Mock Job job;

    @Autowired
    UserRepository userRepository;

    TagMatcher tagMatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tagMatcher = new TagMatcher();
        tagMatcher.setUserRepository(userRepository);
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
        assertNotNull(tagMatcher);
    }

    @Test
    void testTagMatching() {
        when(job.getTags()).thenReturn(getJobTags());
        Set<User> result = tagMatcher.getPotentialHelpers(job);
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

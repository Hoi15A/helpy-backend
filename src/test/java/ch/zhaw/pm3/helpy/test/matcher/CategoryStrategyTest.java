package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.CategoryStrategy;
import ch.zhaw.pm3.helpy.model.category.Category;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryStrategyTest {
    @Autowired
    UserRepository userRepository;

    @Mock Job job;

    CategoryStrategy categoryMatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryMatcher = new CategoryStrategy();
        categoryMatcher.setUserRepository(userRepository);
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
        assertNotNull(categoryMatcher);
    }

    @Test
    void testCategoryMatching() {
        when(job.getCategories()).thenReturn(getJobCategories());
        Collection<User> result = categoryMatcher.getPotentialHelpers(job);
        assertIterableEquals(getExpectedResultList(), result);
    }


    private List<User> getExpectedResultList() {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(userRepository.findById("spidey@email.com").get());
        expectedList.add(userRepository.findById("leandro@email.com").get());
        expectedList.add(userRepository.findById("hawkeye@email.com").get());
        return expectedList;
    }


    private Set<Category> getJobCategories() {
        Set<Category> jobCategories = new HashSet<>();
        jobCategories.add(new Category("Administrativ"));
        jobCategories.add(new Category("Sprache"));
        jobCategories.add(new Category("ÖV"));
        return jobCategories;
    }

}

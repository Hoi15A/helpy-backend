package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.CategoryMatcher;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryMatcherTest {
    @Autowired
    UserRepository userRepository;

    @Mock Job job;

    CategoryMatcher categoryMatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryMatcher = new CategoryMatcher();
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
        Set<User> result = categoryMatcher.getPotentialHelpers(job);
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

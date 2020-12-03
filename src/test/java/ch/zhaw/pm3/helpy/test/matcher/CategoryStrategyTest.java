package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.CategoryStrategy;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryStrategyTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Mock Job job;

    CategoryStrategy categoryMatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryMatcher = new CategoryStrategy();
    }

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
        assertNotNull(categoryRepository);
        assertNotNull(categoryMatcher);
    }

    @Test
    void testCategoryMatchingWithOneCategory() {
        when(job.getCategories()).thenReturn(getCategoriesByName("Sprache"));
        Collection<User> result = categoryMatcher.filterPotentialHelpers(job, new ArrayList<>(userRepository.findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE)));
        assertIterableEquals(getExpectedHelperWithSprache(), result);
    }

    @Test
    void testCategoryMatchingTwoCategories() {
        when(job.getCategories()).thenReturn(getJobCategoriesTwoCategories());
        Collection<User> result = categoryMatcher.filterPotentialHelpers(job, new ArrayList<>(userRepository.findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE)));
        assertIterableEquals(getExpectedHelperWithSprache(), result);
    }

    @Test
    void testNoCategoryButOneRelatedCategories() {
        when(job.getCategories()).thenReturn(getCategoriesByName("Divers"));
        Collection<User> result = categoryMatcher.filterPotentialHelpers(job, new ArrayList<>(userRepository.findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE)));
        assertIterableEquals(getExpectedHelperWithSprache(), result);
    }

    @Test
    void testNoCategoryButTwoRelatedCategories() {
        when(job.getCategories()).thenReturn(getCategoriesByName("Behörden"));
        Collection<User> result = categoryMatcher.filterPotentialHelpers(job, new ArrayList<>(userRepository.findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE)));
        assertIterableEquals(getExpectedHelperWithBehörden(), result);
    }

    @Test
    void testNoCategoryNoRelated() {
        when(job.getCategories()).thenReturn(getCategoriesByName("Sport"));
        Collection<User> result = categoryMatcher.filterPotentialHelpers(job, new ArrayList<>(userRepository.findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE)));
        assertEquals(0, result.size());
    }

    private Set<Category> getJobCategoriesTwoCategories() {
        Set<Category> jobCategories = new HashSet<>();
        jobCategories.add(new Category("Administrativ"));
        jobCategories.add(new Category("Sprache"));

        return jobCategories;
    }

    private List<User> getExpectedHelperWithSprache() {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(userRepository.findById("leandro@email.com").get());
        expectedList.add(userRepository.findById("hawkeye@email.com").get());
        return expectedList;
    }

    private List<User> getExpectedHelperWithBehörden() {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(userRepository.findById("spidey@email.com").get());
        expectedList.add(userRepository.findById("leandro@email.com").get());
        expectedList.add(userRepository.findById("hawkeye@email.com").get());

        return expectedList;
    }

    private Set<Category> getCategoriesByName(String category) {
        Set<Category> jobCategories = new HashSet<>();
        jobCategories.add(categoryRepository.findById(category).get());
        return jobCategories;
    }

}

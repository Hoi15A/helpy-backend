package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryStrategy extends MatcherStrategy {

    private Set<User> users;

    @Override
    public Collection<User> getPotentialHelpers(Job job) {
        users = getUserRepository().findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE);
        return getHelpersWithMatchingCategories(job);
    }

    private Set<User> getHelpersWithMatchingCategories(Job job) {
        return users.stream()
                .filter(User::isWantsToHelpActive)
                .filter(helper -> !Collections.disjoint(helper.getCategories(), job.getCategories())) // at least 1 category in common
                .collect(Collectors.toSet());
    }


}

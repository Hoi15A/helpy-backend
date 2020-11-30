package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class TagMatcher extends MatcherStrategy {

    private Set<User> users;

    @Override
    public Set<User> getPotentialHelpers(Job job) {
        users = getUserRepository().findUsersWithCategoriesAndTagsByStatus(UserStatus.ACTIVE);
        return getHelpersWithMatchingTags(job);
    }

    private Set<User> getHelpersWithMatchingTags(Job job) {
        return users.stream()
                .filter(User::isWantsToHelpActive)
                .filter(helper -> !Collections.disjoint(helper.getTags(), job.getTags()))
                .collect(Collectors.toSet());
    }
}

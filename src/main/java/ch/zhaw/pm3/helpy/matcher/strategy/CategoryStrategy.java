package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryStrategy implements Strategy {

    @Override
    public Collection<User> filterPotentialHelpers(Job job, List<User> userList) {
        return userList.stream()
                .filter(User::isWantsToHelpActive)
                .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                .filter(helper -> !Collections.disjoint(helper.getCategories(), job.getCategories())) // at least 1 category in common
                .collect(Collectors.toSet());
    }
}

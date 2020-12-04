package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryStrategy implements Strategy {

    @Override
    public Collection<User> filterPotentialHelpers(Job job, List<User> userList) {

        Set<Category> jobCategories = job.getCategories();
        Set<Category> relatedCategories = new HashSet<>();
        Set<User> potentialHelpers;

        for(Category category : jobCategories) {
            relatedCategories.addAll(category.getListOfRelated());
        }

        potentialHelpers = userList.stream()
                .filter(User::isWantsToHelpActive)
                .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                .filter(helper -> !Collections.disjoint(helper.getCategories(), job.getCategories())) // at least 1 category in common
                .collect(Collectors.toSet());

        if(potentialHelpers.isEmpty()) {
            potentialHelpers = userList.stream()
                    .filter(User::isWantsToHelpActive)
                    .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                    .filter(helper -> !Collections.disjoint(helper.getCategories(), relatedCategories))
                    .collect(Collectors.toSet());
        }
        return potentialHelpers;
    }
}

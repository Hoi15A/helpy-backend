package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RatingStrategy implements Strategy {
    @Override
    public Collection<User> filterPotentialHelpers(Job job, List<User> userList) {
        Comparator<User> comparator = (o1, o2) -> {
            double u1 = (double) o1.getRatings().stream().reduce(0, Integer::sum) / o1.getRatings().size();
            double u2 = (double) o2.getRatings().stream().reduce(0, Integer::sum) / o2.getRatings().size();
            return Double.compare(u2, u1);
        };

        return userList.stream()
                .filter(User::isWantsToHelpActive)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}

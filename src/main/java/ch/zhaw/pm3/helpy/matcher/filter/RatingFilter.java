package ch.zhaw.pm3.helpy.matcher.filter;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter users by rating.
 */
public class RatingFilter implements Filter {
    @Override
    public Collection<User> filterPotentialHelpers(Job job, List<User> userList) {
        Comparator<User> comparator = (o1, o2) -> {
            double u1 = (double) o1.getRatings().stream().reduce(0, Integer::sum) / o1.getRatings().size();
            double u2 = (double) o2.getRatings().stream().reduce(0, Integer::sum) / o2.getRatings().size();
            return Double.compare(u2, u1);
        };

        return userList.stream()
                .filter(User::isWantsToHelpActive)
                .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}

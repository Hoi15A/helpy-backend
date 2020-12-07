package ch.zhaw.pm3.helpy.matcher.filter;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.Collection;
import java.util.List;

/**
 * Blueprint of a filter.
 */
public interface Filter {

    /**
     * Filter a list of users by matching attributes with the job.
     * @param job to be matched with
     * @param userList to be filtered
     * @return the filtered collection of {@link User}
     */
    Collection<User> filterPotentialHelpers(Job job, List<User> userList);
}

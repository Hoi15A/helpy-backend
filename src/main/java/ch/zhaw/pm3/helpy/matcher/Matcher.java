package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.Collection;

/**
 * Blueprint for a general Matcher.
 */
public interface Matcher {

    /**
     * Get a collection of potential helpers who can do the given {@link Job}
     * @param job to be executed
     * @return the potential helpers as collection of {@link User}
     */
    Collection<User> getPotentialMatches(Job job);
}

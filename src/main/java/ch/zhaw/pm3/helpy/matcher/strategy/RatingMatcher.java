package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.Set;

public class RatingMatcher implements MatcherStrategy {
    @Override
    public Set<User> getPotentialHelpers(Job job) {
        return null;
    }
}

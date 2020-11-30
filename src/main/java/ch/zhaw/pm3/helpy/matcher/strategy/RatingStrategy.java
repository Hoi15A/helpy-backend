package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.Collection;

public class RatingStrategy implements Strategy {
    @Override
    public Collection<User> getPotentialHelpers(Job job) {
        return null;
    }
}

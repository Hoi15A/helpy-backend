package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.List;

public interface MatcherStrategy {
    List<User> getPotentialHelpers(Job job);
}

package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.Set;

public interface Matcher {
    Set<User> potentialMatches(Job job);
}

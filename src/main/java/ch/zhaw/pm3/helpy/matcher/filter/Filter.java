package ch.zhaw.pm3.helpy.matcher.filter;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;

import java.util.Collection;
import java.util.List;

public interface Filter {
    Collection<User> filterPotentialHelpers(Job job, List<User> userList);
}

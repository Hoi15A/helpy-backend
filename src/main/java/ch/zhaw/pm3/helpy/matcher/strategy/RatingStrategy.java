package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.Set;

public class RatingStrategy implements Strategy {
    @Override
    public Collection<User> getPotentialHelpers(Job job) {
        return null;
    }
}

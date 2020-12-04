package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.matcher.filter.LocationFilter;
import ch.zhaw.pm3.helpy.matcher.filter.Filter;
import ch.zhaw.pm3.helpy.matcher.filter.FilterType;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.LocationRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("jobMatcher")
public class JobMatcher implements Matcher {

    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private static final int MIN_USERS = 5;

    @Override
    public Collection<User> getPotentialMatches(Job job) {
        List<FilterType> filterTypes = Arrays.stream(FilterType.values())
                .filter(filterType -> FilterType.LOCATION != filterType)
                .sorted(Comparator.comparingInt(FilterType::getPriority))
                .collect(Collectors.toList());

        LocationFilter locationFilter = (LocationFilter) getFilter(FilterType.LOCATION);
        locationFilter.setLocationRepository(locationRepository);
        locationFilter.setUserRepository(userRepository);

        Collection<User> users = locationFilter.filterPotentialHelpers(job, null);

        users  = users.stream().filter(user -> !user.getEmail().equals(job.getAuthor().getEmail())).collect(Collectors.toList());
        Collection<User> resultCopy = users;
        for (FilterType type: filterTypes) {
            users = getFilter(type).filterPotentialHelpers(job, new ArrayList<>(users));
            if(!type.isMandatory() && users.size() < MIN_USERS) break;
            resultCopy = users;
        }
        return resultCopy;
    }

    private Filter getFilter(FilterType filterType) {
        try {
            return MatcherFactory.getMatcher(filterType);
        } catch (NotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}

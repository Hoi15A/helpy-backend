package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.LocationStrategy;
import ch.zhaw.pm3.helpy.matcher.strategy.Strategy;
import ch.zhaw.pm3.helpy.matcher.strategy.StrategyType;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.LocationRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.NotSupportedException;
import java.util.*;
import java.util.stream.Collectors;

@Component("matcherController")
public class MatcherController implements Matcher {

    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private static final int MIN_USERS = 5;

    @Override
    public Collection<User> getPotentialMatches(Job job) {
        List<StrategyType> strategyTypes = Arrays.stream(StrategyType.values())
                .filter(strategyType -> StrategyType.LOCATION != strategyType)
                .sorted(Comparator.comparingInt(StrategyType::getPriority))
                .collect(Collectors.toList());

        LocationStrategy locationStrategy = (LocationStrategy) getStrategy(StrategyType.LOCATION);
        locationStrategy.setLocationRepository(locationRepository);
        locationStrategy.setUserRepository(userRepository);

        Collection<User> users = locationStrategy.filterPotentialHelpers(job, null); // todo: throws currently error when plz from author not in db

        Collection<User> resultCopy = users;
        for (StrategyType type: strategyTypes) {
            if(!type.isMandatory()) {
                if(users.size() < MIN_USERS) break;
                resultCopy = users;
            }
            users = getStrategy(type).filterPotentialHelpers(job, new ArrayList<>(users));
        }
        return resultCopy;
    }

    private Strategy getStrategy(StrategyType strategyType) {
        try {
            return MatcherFactory.getMatcher(strategyType);
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

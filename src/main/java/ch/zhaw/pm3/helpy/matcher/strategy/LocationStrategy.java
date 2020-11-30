package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.location.Location;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.location.LocationUtil;
import ch.zhaw.pm3.helpy.model.user.UserStatus;

import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class LocationStrategy extends MatcherStrategy {

    @Override
    public Collection<User> getPotentialHelpers(Job job) {
        Location authorLocation = getLocationRepository().getLocationByPlz(job.getAuthor().getPlz());
        List<Location> locations = getLocationRepository().findAll();
        if(authorLocation == null) throw new RecordNotFoundException("Postleitzahl wurde nicht in unserer Datenbank gefunden.");

        ToDoubleFunction<Location> distanceFunktion = (location) -> LocationUtil.calcDistance(authorLocation.getGeolocation(), location.getGeolocation());

        return locations.stream()
                .filter(location -> distanceFunktion.applyAsDouble(location) <= 10.0)
                .sorted(Comparator.comparingDouble(distanceFunktion))
                .map(Location::getPlz)
                .map(getUserRepository()::findUsersByPlz)
                .flatMap(Set::stream)
                .filter(User::isWantsToHelpActive)                              // TODO: auslagern in controller, wen
                .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                .collect(Collectors.toList());
    }

}

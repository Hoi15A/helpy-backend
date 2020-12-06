package ch.zhaw.pm3.helpy.matcher.filter;

import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.location.Location;
import ch.zhaw.pm3.helpy.model.location.LocationUtil;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserStatus;
import ch.zhaw.pm3.helpy.repository.LocationRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import lombok.Setter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Filter users by distance.
 */
@Setter
public class LocationFilter implements Filter {

    public static final double RADIUS_IN_KM = 10.0;
    private UserRepository userRepository;
    private LocationRepository locationRepository;

    @Override
    public Collection<User> filterPotentialHelpers(Job job, List<User> userList) {
        Location authorLocation = locationRepository.getLocationByPlz(job.getAuthor().getPlz());
        if(authorLocation == null) throw new RecordNotFoundException("Postleitzahl wurde nicht in unserer Datenbank gefunden.");

        ToDoubleFunction<Location> distanceFunction = location -> LocationUtil.calcDistance(authorLocation.getGeolocation(), location.getGeolocation());

        List<Integer> filteredPlz = locationRepository.findAll().stream()
                .filter(location -> distanceFunction.applyAsDouble(location) <= RADIUS_IN_KM)
                .sorted(Comparator.comparingDouble(distanceFunction))
                .map(Location::getPlz)
                .collect(Collectors.toList());

        return filteredPlz.stream()
                .map(userRepository::findUsersByPlz)
                .flatMap(Set::stream)
                .filter(User::isWantsToHelpActive)
                .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                .collect(Collectors.toList());
    }
}

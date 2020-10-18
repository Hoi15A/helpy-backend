package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.model.Helper;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides an appropiate list of Helper.
 * @author meletlea
 * @version 18.10.2020
 */
public class JobMatcher {

    /*
     * Steps:
     * - add to matching job
     * - load list of helper near helpseeker (first 2 digits from postcode)
     * - run match algorithm (filter/sorter)
     * - return potential helper
     */

    @Autowired
    private UserRepository userRepository;

    private final Job job;

    public JobMatcher(@NotNull Job job) {
        this.job = job;
    }

    private List<Helper> getHelperNearHelpseeker() {
        List<User> users = new ArrayList<>();
        int jobPlz = job.getAuthor().getPlz(); // PLZ in switzerland is a 4 digit number
        int plzRadius = 3;
        for (int i = -plzRadius; i < plzRadius; i++) {
            int plz = i + jobPlz;
            users.addAll(userRepository.findUsersByPlz(plz));
        }

        return users.stream().filter(Helper.class::isInstance)
                .map(Helper.class::cast)
                .collect(Collectors.toList());
    }

    private List<Helper> match(List<Helper> potentialHelper) {
        return potentialHelper.stream()
                .filter(helper -> helper.getStatus() == UserStatus.ACTIVE)
                .filter(helper -> !Collections.disjoint(helper.getCategories(), job.getCategories())) // at least 1 category in common
                .collect(Collectors.toList());
    }

    private List<Helper> sortByCompatibility(List<Helper> potentialHelper) {
        /*
         * How to calculate compatibility score:
         * - amount of matching categories
         * - amount of matching tags
         * - amount of completed jobs
         * - amount of ratings / mean value
         */
        return potentialHelper;
    }

    public List<Helper> getPotentialHelper() {
        return sortByCompatibility(match(getHelperNearHelpseeker()));
    }

}

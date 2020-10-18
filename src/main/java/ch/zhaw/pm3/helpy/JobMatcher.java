package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.model.Helper;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
    private List<Helper> potentialHelper;


    public JobMatcher(@NotNull Job job) {
        this.job = job;
        potentialHelper = sortByCompatibility(match(getHelperNearHelpseeker()));
    }

    private List<Helper> getHelperNearHelpseeker() {
        return new ArrayList<>();
    }

    private List<Helper> match(List<Helper> potentialHelper) {
        return potentialHelper;
    }

    private List<Helper> sortByCompatibility(List<Helper> potentialHelper) {
        return potentialHelper;
    }

    public List<Helper> getPotentialHelper() {
        return new ArrayList<>();
    }

}

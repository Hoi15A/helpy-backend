package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.matcher.JobMatcher;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for the job matcher.
 * @author meletela
 */
@Transactional
@Service
public class JobMatcherService {

    private final UserRepository userRepository;

    /**
     * Autowired constructor
     * @param userRepository interface to persistence
     */
    public JobMatcherService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns a list of potential helpers
     * @param job to match
     * @return a list of {@link Helper}
     */
    public List<Helper> getPotentialHelpersForJob(Job job) {
        List<User> users = new ArrayList<>();
        int jobPlz = job.getAuthor().getPlz(); // PLZ in switzerland is a 4 digit number
        int plzRadius = 3;
        for (int i = -plzRadius; i < plzRadius; i++) {
            int plz = i + jobPlz;
            users.addAll(userRepository.findUsersByPlz(plz));
        }

        List<Helper> helpersNearHelpseeker =  users.stream()
                .filter(Helper.class::isInstance)
                .map(Helper.class::cast)
                .collect(Collectors.toList());

        JobMatcher jobMatcher = new JobMatcher(job, helpersNearHelpseeker);
        return jobMatcher.getPotentialHelpers();
    }

}

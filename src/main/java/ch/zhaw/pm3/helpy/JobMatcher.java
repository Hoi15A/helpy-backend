package ch.zhaw.pm3.helpy;

import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.model.Category;
import ch.zhaw.pm3.helpy.model.Helper;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.Tag;
import ch.zhaw.pm3.helpy.model.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToLongFunction;
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
        Comparator<Helper> sortingByCompatibility = (h1, h2) -> {
            // categories
            ListScoreCalculator<Category> categoryScoreCalculator = new ListScoreCalculator<>();
            int categoriesScore = categoryScoreCalculator.calc(job.getCategories(), h1.getCategories()) - categoryScoreCalculator.calc(job.getCategories(), h2.getCategories());
            // tags
            ListScoreCalculator<Tag> tagScoreCalculator = new ListScoreCalculator<>();
            int tagsScore = tagScoreCalculator.calc(job.getTags(), h1.getTags()) - tagScoreCalculator.calc(job.getTags(), h2.getTags());
            // completed jobs
            long completedJobScore = calculateCompletedJobsScore(h1.getCompletedJobs()) - calculateCompletedJobsScore(h2.getCompletedJobs());
            // ratings
            int h1RatingsCount = h1.getRatings().size();
            int h2RatingsCount = h2.getRatings().size();
            long ratingScore = h1RatingsCount * sumRatings(h2.getRatings()) - h2RatingsCount * sumRatings(h1.getRatings());

            long totalScore = (categoriesScore + tagsScore + completedJobScore + ratingScore);
            return normalize(totalScore);
        };
        return potentialHelper.stream()
                .sorted(sortingByCompatibility)
                .collect(Collectors.toList());
    }

    private int normalize(long score) {
        if (score == 0) return 0;
        return score > 0 ? 1 : -1;
    }

    private long sumRatings(List<Integer> ratings) {
        return ratings.stream()
                .reduce(Integer::sum)
                .get();
    }

    private static class ListScoreCalculator<T> {

        int calc(List<T> l1, List<T> l2) {
            List<T> commonList = new ArrayList<>(l1);
            commonList.retainAll(l2);
            return commonList.size();
        }

    }

    private long calculateCompletedJobsScore(List<Job> jobs) {
        return jobs.stream()
                .mapToLong(mapJobToScore())
                .reduce(Long::sum)
                .getAsLong();
    }

    private ToLongFunction<Job> mapJobToScore() {
        return toMappingJob -> {
            List<Category> commonCategories = new ArrayList<>(toMappingJob.getCategories());
            commonCategories.retainAll(toMappingJob.getCategories());

            List<Tag> commonTags = new ArrayList<>(toMappingJob.getTags());
            commonTags.retainAll(toMappingJob.getTags());

            return (long) (commonCategories.size() + commonTags.size());
        };
    }

    public List<Helper> getPotentialHelper() {
        return sortByCompatibility(match(getHelperNearHelpseeker()));
    }

}

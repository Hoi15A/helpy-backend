package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.model.Category;
import ch.zhaw.pm3.helpy.model.Helper;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.Tag;
import ch.zhaw.pm3.helpy.model.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Provides an appropriate list of Helper.
 * @author meletlea
 * @version 18.10.2020
 */
@Component
public class JobMatcher {

    private Job job;

    @Autowired
    UserRepository userRepository;

    /**
     * Returns a list of available and compatible helpers, sorted by a compatibility score.
     * @return List<Helper>
     */
    @Transactional
    public List<Helper> getPotentialHelper() {
        return sortByCompatibility(match(getHelperNearHelpseeker()));
    }

    private List<Helper> getHelperNearHelpseeker() {
        List<User> users = new ArrayList<>();
        int jobPlz = job.getAuthor().getPlz(); // PLZ in switzerland is a 4 digit number
        int plzRadius = 3;
        for (int i = -plzRadius; i < plzRadius; i++) {
            int plz = i + jobPlz;
            users.addAll(userRepository.findUsersByPlz(plz));
        }

        return users.stream()
                .filter(Helper.class::isInstance)
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
            int categoryScore = categoryScoreCalculator.calc(job.getCategories(), h1.getCategories()) - categoryScoreCalculator.calc(job.getCategories(), h2.getCategories());
            // tags
            ListScoreCalculator<Tag> tagScoreCalculator = new ListScoreCalculator<>();
            int tagScore = tagScoreCalculator.calc(job.getTags(), h1.getTags()) - tagScoreCalculator.calc(job.getTags(), h2.getTags());
            // completed jobs
            int completedJobScore = calculateCompletedJobsScore(h1.getCompletedJobs()) - calculateCompletedJobsScore(h2.getCompletedJobs());
            // ratings
            int h1RatingsCount = h1.getRatings().size();
            int h2RatingsCount = h2.getRatings().size();
            int ratingScore = h1RatingsCount * sumRatings(h2.getRatings()) - h2RatingsCount * sumRatings(h1.getRatings());

            return (categoryScore + tagScore + completedJobScore + ratingScore);
        };
        return potentialHelper.stream()
                .sorted(sortingByCompatibility)
                .collect(Collectors.toList());
    }

    private int sumRatings(List<Integer> ratings) {
        return ratings.stream().reduce(0, Integer::sum);
    }

    private int calculateCompletedJobsScore(List<Job> jobs) {
        return jobs.stream()
                .mapToInt(mapJobToScore())
                .reduce(0, Integer::sum);
    }

    private ToIntFunction<Job> mapJobToScore() {
        return toMappingJob -> {
            List<Category> commonCategories = new ArrayList<>(toMappingJob.getCategories());
            commonCategories.retainAll(toMappingJob.getCategories());

            List<Tag> commonTags = new ArrayList<>(toMappingJob.getTags());
            commonTags.retainAll(toMappingJob.getTags());

            return commonCategories.size() + commonTags.size();
        };
    }

    private static class ListScoreCalculator<T> {

        int calc(List<T> l1, List<T> l2) {
            List<T> commonList = new ArrayList<>(l1);
            commonList.retainAll(l2);
            return commonList.size();
        }

    }

    public void setJob(Job job) {
        this.job = job;
    }
}

package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.model.user.UserStatus;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Provides an appropriate list of User.
 * @author meletlea
 * @version 18.10.2020
 */
@RequiredArgsConstructor
public class JobMatcher {

    private final Job job;
    private final Set<User> helpersNearHelpseeker;

    /**
     * Returns a list of available and compatible helpers, sorted by a compatibility score.
     * @return List<User>
     */
    public List<User> getPotentialHelpers() {
        return sortByCompatibility(match());
    }

    private Set<User> match() {
        return helpersNearHelpseeker.stream()
                .filter(helper -> helper.getStatus() == UserStatus.ACTIVE)
                .filter(helper -> !Collections.disjoint(helper.getCategories(), job.getCategories())) // at least 1 category in common
                .collect(Collectors.toSet());
    }

    private List<User> sortByCompatibility(Set<User> potentialUsers) {
        Comparator<User> sortingByCompatibility = (h1, h2) -> {
            // categories
            SetScoreCalculator<Category> categoryScoreCalculator = new SetScoreCalculator<>();
            int categoryScore = categoryScoreCalculator.calc(job.getCategories(), h1.getCategories()) - categoryScoreCalculator.calc(job.getCategories(), h2.getCategories());
            // tags
            SetScoreCalculator<Tag> tagScoreCalculator = new SetScoreCalculator<>();
            int tagScore = tagScoreCalculator.calc(job.getTags(), h1.getTags()) - tagScoreCalculator.calc(job.getTags(), h2.getTags());
            // completed jobs
            int completedJobScore = calculateCompletedJobsScore(h1.getCompletedJobs()) - calculateCompletedJobsScore(h2.getCompletedJobs());
            // ratings
            int h1RatingsCount = h1.getRatings().size();
            int h2RatingsCount = h2.getRatings().size();
            int ratingScore = h1RatingsCount * sumRatings(h2.getRatings()) - h2RatingsCount * sumRatings(h1.getRatings());
            return -(categoryScore + tagScore + completedJobScore + ratingScore);
        };
        return potentialUsers.stream()
                .sorted(sortingByCompatibility)
                .collect(Collectors.toList());
    }

    private int sumRatings(List<Integer> ratings) {
        return ratings.stream().reduce(0, Integer::sum);
    }

    private int calculateCompletedJobsScore(Set<Job> jobs) {
        return jobs.stream()
                .mapToInt(mapJobToScore())
                .reduce(0, Integer::sum);
    }

    private ToIntFunction<Job> mapJobToScore() {
        return toMappingJob -> {
            Set<Category> commonCategories = new HashSet<>(toMappingJob.getCategories());
            commonCategories.retainAll(job.getCategories());

            Set<Tag> commonTags = new HashSet<>(toMappingJob.getTags());
            commonTags.retainAll(job.getTags());

            return commonCategories.size() + commonTags.size();
        };
    }

    private static class SetScoreCalculator<T> {

        int calc(Set<T> l1, Set<T> l2) {
            Set<T> commonSet = new HashSet<>(l1);
            commonSet.retainAll(l2);
            return commonSet.size();
        }
    }
}

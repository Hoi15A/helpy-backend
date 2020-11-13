package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;
import lombok.RequiredArgsConstructor;

import java.util.*;
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
    private final List<User> helpersNearHelpseeker;

    /**
     * Returns a list of available and compatible helpers, sorted by a compatibility score.
     * @return List<User>
     */
    public List<User> getPotentialHelpers() {
        return sortByCompatibility(match());
    }

    private List<User> match() {
        return helpersNearHelpseeker.stream()
                .filter(helper -> helper.getStatus() == UserStatus.ACTIVE)
                .filter(helper -> !Collections.disjoint(helper.getCategories(), job.getCategories())) // at least 1 category in common
                .collect(Collectors.toList());
    }

    private List<User> sortByCompatibility(List<User> potentialUsers) {
        Comparator<User> sortingByCompatibility = (h1, h2) -> {
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
            return -(categoryScore + tagScore + completedJobScore + ratingScore);
        };
        return potentialUsers.stream()
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

        int calc(Set<T> l1, Set<T> l2) {
            List<T> commonList = new ArrayList<>(l1);
            commonList.retainAll(l2);
            return commonList.size();
        }

    }
}

package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;

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
public class JobMatcher {

    private final Job job;
    private final List<Helper> helpersNearHelpseeker;

    /**
     * Default constructor
     * @param job containing the data to match
     * @param helpersNearHelpseeker list of Helper to filter
     */
    public JobMatcher(Job job, List<Helper> helpersNearHelpseeker) {
        this.job = job;
        this.helpersNearHelpseeker = helpersNearHelpseeker;
    }

    /**
     * Returns a list of available and compatible helpers, sorted by a compatibility score.
     * @return List<Helper>
     */
    public List<Helper> getPotentialHelpers() {
        return sortByCompatibility(match());
    }

    private List<Helper> match() {
        return helpersNearHelpseeker.stream()
                .filter(helper -> helper.getStatus() == UserStatus.ACTIVE)
                .filter(helper -> !Collections.disjoint(helper.getCategories(), job.getCategories())) // at least 1 category in common
                .collect(Collectors.toList());
    }

    private List<Helper> sortByCompatibility(List<Helper> potentialHelpers) {
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
            return -(categoryScore + tagScore + completedJobScore + ratingScore);
        };
        return potentialHelpers.stream()
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
}

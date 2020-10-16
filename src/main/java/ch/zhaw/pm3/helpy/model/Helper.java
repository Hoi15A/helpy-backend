package ch.zhaw.pm3.helpy.model;

import java.util.List;

public class Helper extends User {
    private List<Integer> ratings;
    private List<Job> completedJobs;
    private List<Category> categories;
    private List<String> tags;

    public Helper() {
        //needed for JPA
    }

    public Helper(UserBuilder userBuilder) {
        super(userBuilder);
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public List<Job> getCompletedJobs() {
        return completedJobs;
    }

    public void setCompletedJobs(List<Job> completedJobs) {
        this.completedJobs = completedJobs;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

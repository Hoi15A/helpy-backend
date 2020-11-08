package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Model class which holds the information for the Helpy helper.
 */
@Entity
public class Helper extends User {
    @ElementCollection
    private List<Integer> ratings;
    @OneToMany(mappedBy = "matchedHelper")
    @JsonBackReference
    private List<Job> completedJobs;
    @ManyToMany
    private Set<Category> categories;
    @ManyToMany
    private Set<Tag> tags;

    /**
     * Creates a new helper instance.
     */
    public Helper() {
        //needed for JPA
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}

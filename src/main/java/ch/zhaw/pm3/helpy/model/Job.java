package ch.zhaw.pm3.helpy.model;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Job {

    @Id
    private long id;
    @NotBlank(message = "You have to enter a title")
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    @NotBlank(message = "You have to enter a title")
    private String description;
    @ManyToOne
    @NotNull(message = "You have to provide an author")
    private Helpseeker author;
    private LocalDate created;
    private JobStatus status;
    @ManyToOne
    private Helper matchedHelper;
    @ManyToMany
    private List<Category> categories;
    @ManyToMany
    private List<Tag> tags;

    public Job() {}

    public Job(String title, String description, Helpseeker author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Helpseeker getAuthor() {
        return author;
    }

    public void setAuthor(Helpseeker author) {
        this.author = author;
    }

    @JsonProperty
    public LocalDate getCreated() {
        return created;
    }

    @JsonIgnore
    public void setCreated(LocalDate created) {
        this.created = created;
    }

    @JsonProperty
    public JobStatus getStatus() {
        return status;
    }

    @JsonIgnore
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Helper getMatchedHelper() {
        return matchedHelper;
    }

    public void setMatchedHelper(Helper matchedHelper) {
        this.matchedHelper = matchedHelper;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}

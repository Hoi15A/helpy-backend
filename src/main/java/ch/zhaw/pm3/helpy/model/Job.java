package ch.zhaw.pm3.helpy.model;

import ch.zhaw.pm3.helpy.constant.JobStatus;

import java.time.LocalDate;

public class Job {

    private long id;
    private String title;
    private String description;
    private Helpseeker author;
    private LocalDate created;
    private JobStatus status;
    private Helper matchedHelper;
    private List<Category> categories;
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public JobStatus getStatus() {
        return status;
    }

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

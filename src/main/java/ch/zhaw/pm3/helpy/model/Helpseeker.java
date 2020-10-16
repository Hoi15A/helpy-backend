package ch.zhaw.pm3.helpy.model;

import java.util.List;

public class Helpseeker extends User {
    private List<Job> tasks;
    private List<String> tags;

    public Helpseeker() {
        //needed for JPA
    }

    public Helpseeker(UserBuilder userBuilder) {
        super(userBuilder);
    }

    public List<Job> getTasks() {
        return tasks;
    }

    public void setTasks(List<Job> tasks) {
        this.tasks = tasks;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

package ch.zhaw.pm3.helpy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Helpseeker extends User {
    @OneToMany
    private List<Job> tasks;
    @ManyToMany
    private List<Tag> tags;

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}

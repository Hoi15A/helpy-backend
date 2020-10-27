package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.model.Job;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Helpseeker extends User {
    @OneToMany(mappedBy = "author")
    @JsonBackReference
    private List<Job> tasks;

    public Helpseeker() {
        //needed for JPA
    }

    public List<Job> getTasks() {
        return tasks;
    }

    public void setTasks(List<Job> tasks) {
        this.tasks = tasks;
    }
}

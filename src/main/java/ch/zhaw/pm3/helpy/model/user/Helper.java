package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Model class which holds the information for the Helpy helper.
 */
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
public class Helper extends User {

    @ElementCollection
    private List<Integer> ratings;

    @OneToMany(mappedBy = "matchedHelper")
    @JsonBackReference
    private List<Job> completedJobs;

    @ManyToMany
    private List<Category> categories;

    @ManyToMany
    private List<Tag> tags;

}

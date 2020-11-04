package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Model class which holds the information for the Helpy helper.
 */
//TODO: why is equalsandhascode callSuper=True not working?
@NoArgsConstructor
@Getter
@Setter
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

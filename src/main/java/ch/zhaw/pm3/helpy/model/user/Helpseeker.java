package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.model.Job;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Model class which holds the information for the Helpy help seeker.
 */
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
public class Helpseeker extends User {

    @OneToMany(mappedBy = "author")
    @JsonBackReference
    private List<Job> tasks;

}

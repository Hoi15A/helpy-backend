package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Model class which holds the information for the helpy user.
 */
@Data @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // @Builder calls @AllArgsConstructor on public mode
@NoArgsConstructor // needed for @entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {

    //----------------------------------------------------------------
    // general user
    //----------------------------------------------------------------
    @EqualsAndHashCode.Include
    @Id
    private String email;
    private String firstname;
    private String lastname;
    private char sex;
    private int plz;
    private LocalDate birthdate;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    private String biographie;
    private UserStatus status;
    private Permission permission;

    @ToString.Exclude
    @ElementCollection
    private Set<Weekdays> availableWeekDays;

    private boolean wantsToHelpActive;

    //----------------------------------------------------------------
    // helpseeker
    //----------------------------------------------------------------
    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    @JsonBackReference("tasks")
    private Set<Job> tasks;

    //----------------------------------------------------------------
    // helper
    //----------------------------------------------------------------
    @ToString.Exclude
    @ElementCollection
    private List<Integer> ratings;

    @ToString.Exclude
    @OneToMany(mappedBy = "matchedHelper")
    @JsonBackReference("jobs")
    private Set<Job> completedJobs;

    @ToString.Exclude
    @ManyToMany
    private Set<Category> categories;

    @ToString.Exclude
    @ManyToMany
    private Set<Tag> tags;
}

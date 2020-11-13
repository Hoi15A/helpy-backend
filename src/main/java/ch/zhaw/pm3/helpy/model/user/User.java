package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.constant.Permission;
import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.constant.Weekdays;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Model class which holds the information for the helpy user.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
//@JsonSubTypes({@JsonSubTypes.Type(Helper.class), @JsonSubTypes.Type(Helpseeker.class)})
public class User {

    //----------------------------------------------------------------
    // general user
    //----------------------------------------------------------------
    @EqualsAndHashCode.Include
    @Id
    @Email(message = "You have to enter a valid email address")
    private String email;

    @NotBlank(message = "You have to enter your first name")
    private String firstname;

    @NotBlank(message = "You have to enter your last name")
    private String lastname;

    @NotNull(message = "You have to enter your sex")
    private char sex;

    @NotNull(message = "You have to enter the postcode")
    private int plz;

    @NotNull(message = "You have to enter your birthdate")
    private LocalDate birthdate;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    @NotEmpty(message = "You have to enter your biographie")
    private String biographie;

    @NotNull(message = "You have to enter a status")
    private UserStatus status;

    @NotNull(message = "You have to enter a permission type")
    private Permission permission;

    @ToString.Exclude
    @ElementCollection
    private List<Weekdays> availableWeekDays;

    private boolean wantsToHelpActive;

    //----------------------------------------------------------------
    // helpseeker
    //----------------------------------------------------------------
    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    @JsonBackReference("tasks")
    private List<Job> tasks;

    //----------------------------------------------------------------
    // helper
    //----------------------------------------------------------------
    @ToString.Exclude
    @ElementCollection
    private List<Integer> ratings;

    @ToString.Exclude
    @OneToMany(mappedBy = "matchedHelper")
    @JsonBackReference("jobs")
    private List<Job> completedJobs;

    @ToString.Exclude
    @ManyToMany
    private Set<Category> categories;

    @ToString.Exclude
    @ManyToMany
    private Set<Tag> tags;
}

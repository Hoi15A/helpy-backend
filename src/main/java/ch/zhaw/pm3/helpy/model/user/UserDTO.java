package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.constant.Permission;
import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.constant.Weekdays;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Model class which holds the information for the helpy user.
 */
@Data @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTO {

    //----------------------------------------------------------------
    // general user
    //----------------------------------------------------------------
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

    @NotEmpty(message = "You have to enter your biographie")
    private String biographie;

    @NotNull(message = "You have to enter a status")
    private UserStatus status;

    @NotNull(message = "You have to enter a permission type")
    private Permission permission;

    @ToString.Exclude
    private List<Weekdays> availableWeekDays;

    private boolean wantsToHelpActive;

    //----------------------------------------------------------------
    // helpseeker
    //----------------------------------------------------------------
    @ToString.Exclude
    private List<Job> tasks;

    //----------------------------------------------------------------
    // helper
    //----------------------------------------------------------------
    @ToString.Exclude
    private List<Integer> ratings;

    @ToString.Exclude
    private List<Job> completedJobs;

    @ToString.Exclude
    private Set<Category> categories;

    @ToString.Exclude
    private Set<Tag> tags;
}

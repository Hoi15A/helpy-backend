package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.category.CategoryDTO;
import ch.zhaw.pm3.helpy.model.category.TagDTO;
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
    @Email(message = "Die E-Mail Adresse ist ungültig")
    @NotBlank(message = "Es wurde keine E-Mail angegeben")
    private String email;

    @NotBlank(message = "Es wurde kein Vorname angegeben")
    private String firstname;

    @NotBlank(message = "Es wurde kein Nachname angegeben")
    private String lastname;

    @NotNull(message = "Dein Geschlecht wurde nicht angegeben")
    private char sex;

    @NotNull(message = "Deine Postleitzahl wurde nicht angegeben")
    private int plz;

    @NotNull(message = "Dein Geburtsdatum wurde nicht angegeben")
    private LocalDate birthdate;

    @NotBlank(message = "Es wurde kein Passwort angegeben")
    @Getter(onMethod_=@JsonIgnore)
    @Setter(onMethod_=@JsonProperty)
    private String password;

    @NotEmpty(message = "Deine Biographie wurde nicht ausgefüllt")
    private String biographie;

    @NotNull(message = "Dir wurde leider keinen Status für dein Profil gesetzt")
    private UserStatus status;

    @NotNull(message = "Dir wurde leider keine Berechtigung erteilt")
    private Permission permission;

    @ToString.Exclude
    private Set<Weekdays> availableWeekDays;

    private boolean wantsToHelpActive;

    //----------------------------------------------------------------
    // helpseeker
    //----------------------------------------------------------------
    @ToString.Exclude
    private Set<Job> tasks;

    //----------------------------------------------------------------
    // helper
    //----------------------------------------------------------------
    @ToString.Exclude
    private List<Integer> ratings;

    @ToString.Exclude
    private Set<Job> completedJobs;

    @ToString.Exclude
    private Set<CategoryDTO> categories;

    @ToString.Exclude
    private Set<TagDTO> tags;
}

package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.constant.Permission;
import ch.zhaw.pm3.helpy.constant.UserStatus;
import ch.zhaw.pm3.helpy.constant.Weekdays;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Model class which holds the information for the helpy user.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(Helper.class), @JsonSubTypes.Type(Helpseeker.class)})
public class User {

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

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

}

package ch.zhaw.pm3.helpy.model.user;

import ch.zhaw.pm3.helpy.constant.Permission;
import ch.zhaw.pm3.helpy.constant.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(Helper.class), @JsonSubTypes.Type(Helpseeker.class)})
public class User {
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
    @NotBlank(message = "You have to provide a password")
    @Size(message = "Your password has to between 8 and 32 characters", min = 8, max = 32)
    private String password;
    @Column(columnDefinition = "LONGTEXT")
    @NotEmpty(message = "You have to enter your biographie")
    private String biographie;
    @NotNull(message = "You have to enter a status")
    private UserStatus status;
    @NotNull(message = "You have to enter a permission type")
    private Permission permission;

    public User() {
        //needed for JPA
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}

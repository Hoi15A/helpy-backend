package ch.zhaw.pm3.helpy.model;

import ch.zhaw.pm3.helpy.constant.Permission;
import ch.zhaw.pm3.helpy.constant.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
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
    @JsonIgnore
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

    public User(UserBuilder builder) {
        User user = builder.build();
        firstname = user.firstname;
        lastname = user.lastname;
        email = user.email;
        sex = user.sex;
        plz = user.plz;
        birthdate = user.birthdate;
        password = user.password;
        biographie = user.biographie;
        status = user.status;
        permission = user.permission;
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

    public String getPassword() {
        return password;
    }

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

    public static class UserBuilder {
        User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder(User user) {
            this.user = user;
        }

        public UserBuilder setFirstname(String firstname) {
            user.firstname = firstname;
            return this;
        }

        public UserBuilder setLastname(String lastname) {
            user.lastname = lastname;
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder setSex(char sex) {
            user.sex = sex;
            return this;
        }

        public UserBuilder setPlz(int plz) {
            user.plz = plz;
            return this;
        }

        public UserBuilder setBirthdate(LocalDate birthdate) {
            user.birthdate = birthdate;
            return this;
        }

        public UserBuilder setPassword(String password) {
            user.password = password;
            return this;
        }
        public UserBuilder setBiographie(String biographie) {
            user.biographie = biographie;
            return this;
        }

        public UserBuilder setStatus(UserStatus status) {
            user.status = status;
            return this;
        }

        public UserBuilder setPermission(Permission permission) {
            user.permission = permission;
            return this;
        }

        public User build() {
            return user;
        }

    }

}

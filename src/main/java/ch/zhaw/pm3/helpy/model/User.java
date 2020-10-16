package ch.zhaw.pm3.helpy.model;

import ch.zhaw.pm3.helpy.constant.Permission;
import ch.zhaw.pm3.helpy.constant.UserStatus;

import java.time.LocalDate;

public class User {

    private String firstname;
    private String lastname;
    private String email;
    private char sex;
    private int plz;
    private LocalDate birthdate;
    private String password;
    private String biographie;
    private UserStatus status;
    private Permission permission;

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

package ch.zhaw.pm3.helpy.model;

import javax.persistence.Entity;

@Entity
public class Tag extends Category{
    public Tag() {
        //needed for JPA
    }
    public Tag(String name) {
        super(name);
    }
}

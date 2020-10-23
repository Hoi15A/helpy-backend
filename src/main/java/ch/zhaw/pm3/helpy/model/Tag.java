package ch.zhaw.pm3.helpy.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Tag extends Category{
    public Tag() {
        //needed for JPA
    }
    public Tag(String name) {
        super(name);
    }

}

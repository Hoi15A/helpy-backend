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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return getName().equals(tag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + getName() + '\'' +
                '}';
    }

}

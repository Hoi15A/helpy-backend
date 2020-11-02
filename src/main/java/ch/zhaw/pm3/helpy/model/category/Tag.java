package ch.zhaw.pm3.helpy.model.category;

import javax.persistence.Entity;

/**
 * Provides a tag representation.
 */
@Entity
public class Tag extends Category {
    /**
     * Creates a new tag instance.
     */
    public Tag() {
        //needed for JPA
    }

    /**
     * Creates a new tag instance.
     * @param name String
     */
    public Tag(String name) {
        super(name);
    }

}

package ch.zhaw.pm3.helpy.model.category;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * Provides a tag representation.
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Tag extends Category {

    /**
     * Creates a new tag instance.
     * @param name String
     */
    public Tag(String name) {
        super(name);
    }

}

package ch.zhaw.pm3.helpy.model.category;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Provides a tag representation.
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagDTO extends CategoryDTO {

    /**
     * Creates a new tag instance.
     * @param name String
     */
    public TagDTO(String name) {
        super(name);
    }

}

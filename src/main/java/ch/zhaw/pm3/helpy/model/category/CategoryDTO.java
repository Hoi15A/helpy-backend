package ch.zhaw.pm3.helpy.model.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides a category representation.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter
public class CategoryDTO {

    @EqualsAndHashCode.Include
    @NotBlank(message = "Der Name der Kategorie wurde nicht gesetzt")
    @NonNull
    private String name;

    private Set<CategoryDTO> listOfRelated = new HashSet<>();

}

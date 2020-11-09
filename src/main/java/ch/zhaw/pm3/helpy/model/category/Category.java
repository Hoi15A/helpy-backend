package ch.zhaw.pm3.helpy.model.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides a category representation.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Category {

    @EqualsAndHashCode.Include
    @Id
    @NotBlank(message = "You have to provide a name for the category")
    @NonNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> listOfRelated = new ArrayList<>();

    @Column(columnDefinition = "LONGTEXT")
    @NotBlank(message = "You have to provide a description for the category")
    private String description = "No Description has been added";

    public boolean addToRelated(Category category) {
        Objects.requireNonNull(category);
        return listOfRelated.add(category);
    }

    public boolean removeFromRelated(Category category) {
        Objects.requireNonNull(category);
        return listOfRelated.remove(category);
    }

}

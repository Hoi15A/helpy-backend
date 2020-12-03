package ch.zhaw.pm3.helpy.model.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides a category representation.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter
@Entity
public class Category {

    @EqualsAndHashCode.Include
    @Id
    @NonNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> listOfRelated = new HashSet<>();

}

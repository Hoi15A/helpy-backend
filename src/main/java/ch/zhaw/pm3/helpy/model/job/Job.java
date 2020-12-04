package ch.zhaw.pm3.helpy.model.job;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Set;

/**
 * Model class which holds the information for the Helpy job.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) // @Builder calls @AllArgsConstructor on public mode
@NoArgsConstructor // needed for @entity
@Data @Builder
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne
    @JoinColumn(
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (author_email) REFERENCES user ON UPDATE CASCADE"
            )
    )
    private User author;

    private LocalDate created;

    private JobStatus status;

    @ManyToOne
    @JoinColumn(
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (matched_helper_email) REFERENCES user ON UPDATE CASCADE"
            )
    )
    private User matchedHelper;

    @ManyToMany
    private Set<Category> categories;
    @ManyToMany
    private Set<Tag> tags;
    private LocalDate dueDate;

}

package ch.zhaw.pm3.helpy.model;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * Model class which holds the information for the Helpy job.
 */
@NoArgsConstructor
@Data
@Entity
public class Job {

    @Getter(onMethod = @__({@JsonProperty}))
    @Setter(onMethod = @__({@JsonIgnore}))
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "You have to enter a title")
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    @NotBlank(message = "You have to provide a description")
    private String description;

    @ManyToOne
    //@NotNull(message = "You have to provide an author")
    private Helpseeker author;

    @Getter(onMethod = @__({@JsonProperty}))
    @Setter(onMethod = @__({@JsonIgnore}))
    private LocalDate created;

    @Getter(onMethod = @__({@JsonProperty}))
    @Setter(onMethod = @__({@JsonIgnore}))
    private JobStatus status;

    @ManyToOne
    private Helper matchedHelper;

    @ManyToMany
    private List<Category> categories;

    @ManyToMany
    private List<Tag> tags;

    private LocalDate dueDate;
    
}

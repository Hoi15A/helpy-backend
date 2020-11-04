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
/* // TODO: why is this not working
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Getter(onMethod = @__({@Id}))*/
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
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate created;
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private JobStatus status;
    @ManyToOne
    private Helper matchedHelper;
    @ManyToMany
    private List<Category> categories;
    @ManyToMany
    private List<Tag> tags;
    private LocalDate dueDate;


    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }

}

package ch.zhaw.pm3.helpy.model;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

/**
 * Model class which holds the information for the Helpy job.
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // @Builder calls @AllArgsConstructor on public mode
@Data
public class JobDTO {

    @Getter(onMethod = @__({@JsonProperty}))
    @Setter(onMethod = @__({@JsonIgnore}))
    private long id;

    @NotBlank(message = "You have to enter a title")
    private String title;

    @NotBlank(message = "You have to provide a description")
    private String description;

    //@NotNull(message = "You have to provide an author")
    private UserDTO author;

    @Getter(onMethod = @__({@JsonProperty}))
    @Setter(onMethod = @__({@JsonIgnore}))
    private LocalDate created;

    @Getter(onMethod = @__({@JsonProperty}))
    @Setter(onMethod = @__({@JsonIgnore}))
    private JobStatus status;

    private UserDTO matchedHelper;

    private Set<Category> categories;

    private Set<Tag> tags;

    private LocalDate dueDate;

}

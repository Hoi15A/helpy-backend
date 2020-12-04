package ch.zhaw.pm3.helpy.model.job;

import ch.zhaw.pm3.helpy.model.category.CategoryDTO;
import ch.zhaw.pm3.helpy.model.category.TagDTO;
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
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * Model class which holds the information for the Helpy job.
 */
@Data @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // @Builder calls @AllArgsConstructor on public mode
public class JobDTO {

    @Getter(onMethod_=@JsonProperty)
    @Setter(onMethod_=@JsonIgnore)
    private long id;

    @NotBlank(message = "Der Titel des Jobs fehlt")
    private String title;

    @NotBlank(message = "Die Beschreibung des Jobs fehlt")
    private String description;

    //@NotNull(message = "You have to provide an author")
    private UserDTO author;

    @Getter(onMethod_=@JsonProperty)
    @Setter(onMethod_=@JsonIgnore)
    private LocalDate created;

    @Getter(onMethod_=@JsonProperty)
    @Setter(onMethod_=@JsonIgnore)
    private JobStatus status;

    private UserDTO matchedHelper;

    private Set<CategoryDTO> categories;

    private Set<TagDTO> tags;

    @NotNull(message = "Sie müssen ein Abschlussdatum definieren")
    private LocalDate dueDate;

}

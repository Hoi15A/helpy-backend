package ch.zhaw.pm3.helpy.service.util;

import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.CategoryDTO;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.category.TagDTO;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.job.JobDTO;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserDTO;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility to map model and data transfer objects.
 */
@UtilityClass
public class DTOMapper {

    // ---------------------------------------------------------------------------------
    // Category / Tags
    // ---------------------------------------------------------------------------------
    /**
     * Map a data transfer object to a {@link Category}
     * @param dto {@link CategoryDTO}
     * @return the mapped {@link Category}
     */
    public Category mapDTOToCategory(CategoryDTO dto) {
        Category category = new Category(dto.getName());
        category.setListOfRelated(mapDTOsToCategories(dto.getListOfRelated()));
        return category;
    }

    /**
     * Map a {@link Category} to a data transfer object
     * @param category {@link Category}
     * @return the mapped {@link CategoryDTO}
     */
    public CategoryDTO mapCategoryToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO(category.getName());
        dto.setListOfRelated(mapCategoriesToDTOs(category.getListOfRelated()));
        return dto;
    }

    /**
     * Map a set of {@link Category} to a set of data transfer objects
     * @param categories set of {@link Category}
     * @return the mapped {@link CategoryDTO} set
     */
    public Set<CategoryDTO> mapCategoriesToDTOs(Set<Category> categories) {
        if (categories == null) return new HashSet<>();
        return categories.stream()
                .map(DTOMapper::mapCategoryToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Map a set of data transfer objects to a set of {@link Category}
     * @param dtos set of {@link CategoryDTO}
     * @return the mapped {@link Category} set
     */
    public Set<Category> mapDTOsToCategories(Set<CategoryDTO> dtos) {
        if (dtos == null) return new HashSet<>();
        return dtos.stream()
                .map(DTOMapper::mapDTOToCategory)
                .collect(Collectors.toSet());
    }

    /**
     * Map a set of {@link Tag} to a set of data transfer objects
     * @param tags set of {@link Tag}
     * @return the mapped {@link TagDTO} set
     */
    public Set<TagDTO> mapTagsToDTOs(Set<Tag> tags) {
        if (tags == null) return new HashSet<>();
        return tags.stream()
                .map(tag -> new TagDTO(tag.getName()))
                .collect(Collectors.toSet());
    }

    /**
     * Map a set of data transfer objects to a set of {@link Tag}
     * @param dtos set of {@link TagDTO}
     * @return the mapped {@link Tag} set
     */
    public Set<Tag> mapDTOsToTags(Set<TagDTO> dtos) {
        if (dtos == null) return new HashSet<>();
        return dtos.stream()
                .map(dto -> new Tag(dto.getName()))
                .collect(Collectors.toSet());
    }

    // ---------------------------------------------------------------------------------
    // Jobs
    // ---------------------------------------------------------------------------------
    /**
     * Map a set of {@link Job} to a set of data transfer objects
     * @param jobs set of {@link Job}
     * @return the mapped {@link JobDTO} set
     */
    public Set<JobDTO> mapJobsToDTOs(Set<Job> jobs) {
        return jobs.stream().map(DTOMapper::mapJobToDTO).collect(Collectors.toSet());
    }

    /**
     * Map a {@link Job} to a data transfer object
     * @param job {@link Job}
     * @return the mapped {@link JobDTO}
     */
    public JobDTO mapJobToDTO(Job job) {
        if (job == null) return null;
        return JobDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .dueDate(job.getDueDate())
                .created(job.getCreated())
                .status(job.getStatus())
                .author(mapUserToDTO(job.getAuthor()))
                .matchedHelper(mapUserToDTO(job.getMatchedHelper()))
                .categories(mapCategoriesToDTOs(job.getCategories()))
                .tags(mapTagsToDTOs(job.getTags()))
                .build();
    }

    /**
     * Map a data transfer object to a {@link Job}
     * @param dto {@link JobDTO}
     * @return the mapped {@link Job}
     */
    public Job mapDTOToJob(JobDTO dto) {
        if (dto == null) return null;
        // id, status and created set JsonIgnore
        return Job.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .author(mapDTOToUser(dto.getAuthor()))
                .matchedHelper(mapDTOToUser(dto.getMatchedHelper()))
                .categories(mapDTOsToCategories(dto.getCategories()))
                .tags(mapDTOsToTags(dto.getTags()))
                .build();
    }

    // ---------------------------------------------------------------------------------
    // User
    // ---------------------------------------------------------------------------------
    /**
     * Map a list of {@link User} to a list of data transfer objects
     * @param users list of {@link User}
     * @return the mapped {@link UserDTO} list
     */
    public List<UserDTO> mapUsersToDTOs(List<User> users) {
        return users.stream().map(DTOMapper::mapUserToDTO).collect(Collectors.toList());
    }

    /**
     * Map a {@link User} to a data transfer objects
     * @param user list of {@link User}
     * @return the mapped {@link UserDTO}
     */
    public UserDTO mapUserToDTO(User user) {
        if (user == null) return null;
        // password get JsonIgnore
        return UserDTO.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .sex(user.getSex())
                .plz(user.getPlz())
                .birthdate(user.getBirthdate())
                .biographie(user.getBiographie())
                .status(user.getStatus())
                .permission(user.getPermission())
                .availableWeekDays(user.getAvailableWeekDays())
                .wantsToHelpActive(user.isWantsToHelpActive())
                .tasks(user.getTasks())
                .ratings(user.getRatings())
                .completedJobs(user.getCompletedJobs())
                .categories(mapCategoriesToDTOs(user.getCategories()))
                .tags(mapTagsToDTOs(user.getTags()))
                .build();
    }

    /**
     * Map a data transfer objects to a {@link User}
     * @param dto list of {@link UserDTO}
     * @return the mapped {@link User}
     */
    public User mapDTOToUser(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .email(dto.getEmail())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .sex(dto.getSex())
                .plz(dto.getPlz())
                .birthdate(dto.getBirthdate())
                .password(dto.getPassword())
                .biographie(dto.getBiographie())
                .status(dto.getStatus())
                .permission(dto.getPermission())
                .availableWeekDays(dto.getAvailableWeekDays())
                .wantsToHelpActive(dto.isWantsToHelpActive())
                .tasks(dto.getTasks())
                .ratings(dto.getRatings())
                .completedJobs(dto.getCompletedJobs())
                .categories(mapDTOsToCategories(dto.getCategories()))
                .tags(mapDTOsToTags(dto.getTags()))
                .build();
    }
}

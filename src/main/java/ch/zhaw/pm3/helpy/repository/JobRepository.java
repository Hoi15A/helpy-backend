package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

/**
 * Repository for the {@link Job} entity.
 */
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Query to get a set of Jobs from the database whose status attribute is set to the given status
     * @param status to match with a job's status attribute
     * @return set of Jobs with given status
     */
    @Query(value = "select j from Job j where j.status=?1")
    Set<Job> findJobsByStatus(JobStatus status);

    /**
     * Query to get a set of Jobs from the database whose date attribute is set to the given status
     * @param date to match with a job's date attribute
     * @return set of Jobs with given date
     */
    @Query(value = "select j from Job j where j.created=?1")
    Set<Job> findJobsByDate(LocalDate date);

    /**
     * Query to get a set of Jobs from the database whose helpseeker attribute is set to the given helpseeker
     * @param user to match with a Job's helpseeker attribute
     * @return set of Jobs with given helpseeker
     */
    @Query(value = "select j from Job j where j.author=?1")
    Set<Job> findJobsByAuthor(User user);

    /**
     * Query to get a set of Jobs from the database whose matchedHelper attribute is set to the given helper
     * @param user to match with a Job's matchedHelper attribute
     * @return set of Jobs with given helper
     */
    @Query(value = "select j from Job j where j.matchedHelper=?1")
    Set<Job> findJobsByMatchedHelper(User user);

    /**
     * Query to get a set of Jobs from the database whose set of categories includes the given category
     * @param category to match with a job's categories
     * @return set of Jobs that include the given category
     */
    @Query("select j from Job j join j.categories c where c.name = ?1")
    Set<Job> findJobsByCategory(String category);

    /**
     * Query to get a set of Jobs from the database whose set of tags includes the given tag
     * @param tag to match with a job's tags
     * @return set of Jobs that include the given tag
     */
    @Query("select j from Job j join j.tags t where t.name = ?1")
    Set<Job> findJobsByTag(String tag);

    /**
     * Query to remove a {@link User} from all jobs
     * @param email from {@link User}
     */
    @Modifying
    @Query("update Job j set j.matchedHelper = null where j.matchedHelper.email=?1")
    void removeHelperFromJob(String email);

    /**
     * Query to remove a {@link User} aka the author from all jobs
     * @param email from author
     */
    @Modifying
    @Query("update Job j set j.author = null, j.status=2 where j.author.email=?1")
    void removeAuthorFromJob(String email);

}

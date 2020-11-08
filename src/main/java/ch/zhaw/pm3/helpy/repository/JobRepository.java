package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for the {@link Job} entity.
 */
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Query to get a list of Jobs from the database whose status attribute is set to the given status
     * @param status to match with a job's status attribute
     * @return list of Jobs with given status
     */
    @Query(value = "select j from Job j where j.status=?1")
    List<Job> findJobsByStatus(JobStatus status);

    /**
     * Query to get a list of Jobs from the database whose date attribute is set to the given status
     * @param date to match with a job's date attribute
     * @return list of Jobs with given date
     */
    @Query(value = "select j from Job j where j.created=?1")
    List<Job> findJobsByDate(LocalDate date);

    /**
     * Query to get a list of Jobs from the database whose helpseeker attribute is set to the given helpseeker
     * @param helpseeker to match with a Job's helpseeker attribute
     * @return list of Jobs with given helpseeker
     */
    @Query(value = "select j from Job j where j.author=?1")
    List<Job> findJobsByAuthor(Helpseeker helpseeker);

    /**
     * Query to get a list of Jobs from the database whose matchedHelper attribute is set to the given helper
     * @param helper to match with a Job's matchedHelper attribute
     * @return list of Jobs with given helper
     */
    @Query(value = "select j from Job j where j.matchedHelper=?1")
    List<Job> findJobsByMatchedHelper(Helper helper);

    /**
     * Query to get a list of Jobs from the database whose list of categories includes the given category
     * @param category to match with a job's categories
     * @return list of Jobs that include the given category
     */
    @Query("select j from Job j join j.categories c where c.name = ?1")
    List<Job> findJobsByCategory(String category);

    /**
     * Query to get a list of Jobs from the database whose list of tags includes the given tag
     * @param tag to match with a job's tags
     * @return list of Jobs that include the given tag
     */
    @Query("select j from Job j join j.tags t where t.name = ?1")
    List<Job> findJobsByTag(String tag);

    /**
     * Query to remove a {@link ch.zhaw.pm3.helpy.model.user.Helper} from all jobs
     * @param email from {@link ch.zhaw.pm3.helpy.model.user.Helper}
     */
    @Modifying
    @Query("update Job j set j.matchedHelper = null where j.matchedHelper.email=?1")
    void removeHelperFromJob(String email);

    /**
     * Query to remove a {@link Helpseeker} aka the author from all jobs
     * @param email from author
     */
    @Modifying
    @Query("update Job j set j.author = null, j.status=2 where j.author.email=?1")
    void removeAuthorFromJob(String email);

}

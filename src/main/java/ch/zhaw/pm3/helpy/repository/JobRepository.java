package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
    List<Job> findJobsByDate(String date);

    /**
     * Query to get a list of Jobs from the database whose helpseeker attribute is set to the given helpseeker
     * @param helpseeker to match with a Job's helpseeker attribute
     * @return list of Jobs with given helpseeker
     */
    @Query(value = "select j from Job j where j.author=?1")
    List<Job> findJobsByAuthor(Helpseeker helpseeker);

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
}

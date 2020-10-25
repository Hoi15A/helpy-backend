package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "select j from Job j where j.status=?1")
    List<Job> findJobsByStatus(JobStatus status);

    @Query(value = "select j from Job j where j.created=?1")
    List<Job> findJobsByDate(String date);

    @Query(value = "select j from Job j where j.author=?1")
    List<Job> findJobsByAuthor(Helpseeker helpseeker);

    @Query("select j from Job j join j.categories c where c.name = ?1")
    List<Job> findJobsByCategory(String category);

    @Query("select j from Job j join j.tags t where t.name = ?1")
    List<Job> findJobsByTag(String tag);
}

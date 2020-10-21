package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.Helpseeker;
import ch.zhaw.pm3.helpy.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "select * from Job where status=?1", nativeQuery = true)
    List<Job> findJobsByStatus(String status);

    @Query(value = "select * from Job where created=?1", nativeQuery = true)
    List<Job> findJobsByDate(String date);

    @Query(value = "select * from Job where author_email=?1", nativeQuery = true)
    List<Job> findJobsByAuthor(Helpseeker helpseeker);

    @Query(value = "select * from Job as j where exists (select 1 from JOB_CATEGORIES where CATEGORIES_NAME=?1 and j.id=JOB_ID)", nativeQuery = true)
    List<Job> findJobsByCategory(String category);

    @Query(value = "select * from Job as j where exists (select 1 from JOB_TAGS where TAGS_NAME=?1 and j.id=JOB_ID)", nativeQuery = true)
    List<Job> findJobsByTag(String tag);
}

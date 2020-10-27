package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.matcher.JobMatcher;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author meletela
 * @version 26.10.2020
 */
@Transactional
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobMatcherService jobMatcherService;
    private final UserRepository userRepository;

    /**
     * Default constructor
     * @param jobRepository
     * @param jobMatcherService
     * @param userRepository
     */
    public JobService(JobRepository jobRepository, JobMatcherService jobMatcherService, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.jobMatcherService = jobMatcherService;
        this.userRepository = userRepository;
    }

    /**
     * Get all jobs from persistence.
     * @return a list of jobs
     */
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    /**
     * Get a job by identifier
     * @param id job identifier
     * @return the job or {@link RecordNotFoundException} when the job is not existent
     */
    public Job getJobById(long id) {
        Optional<Job> job = jobRepository.findById(id);
        if(job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        return job.get();
    }

    /**
     * Get a list of jobs by status
     * @param status the {@link JobStatus} to search in the jobs
     * @return a list of jobs
     */
    public List<Job> getJobsByStatus(JobStatus status) {
        return jobRepository.findJobsByStatus(status);
    }

    /**
     * Get a list of jobs by author
     * @param email author identifier
     * @return a list of jobs
     */
    public List<Job> getJobsByAuthor(String email) {
        Helpseeker helpseeker = userRepository.findHelpseekerByEmail(email);
        if (helpseeker == null) throw new RecordNotFoundException(email);
        return jobRepository.findJobsByAuthor(helpseeker);
    }

    /**
     * Get a list of jobs by {@link Category}
     * @param category the {@link Category} to search in the jobs as String
     * @return a list of jobs
     */
    public List<Job> getJobsByCategory(String category) {
        return jobRepository.findJobsByCategory(category);
    }

    /**
     * Get a set of jobs containing elements from a list of {@link Category}
     * @param categories list of {@link Category} as array
     * @return a set of jobs
     */
    public Set<Job> getJobsByCategories(Category[] categories) {
        Set<Job> jobs = new HashSet<>();
        for (Category category : categories) {
            jobs.addAll(jobRepository.findJobsByCategory(category.getName()));
        }
        return jobs;
    }

    /**
     * Get a list of jobs by {@link Tag}
     * @param tag the {@link Tag} to search in the jobs as String
     * @return a list of jobs
     */
    public List<Job> getJobsByTag(String tag) {
        return jobRepository.findJobsByTag(tag);
    }

    /**
     * Get a set of jobs containing elements from a list of {@link Tag}
     * @param tags list of {@link Tag} as array
     * @return a list of jobs
     */
    public Set<Job> getJobsByTags(Tag[] tags) {
        Set<Job> jobs = new HashSet<>();
        for (Tag tag : tags) {
            jobs.addAll(jobRepository.findJobsByTag(tag.getName()));
        }
        return jobs;
    }

    /**
     * Get a list of jobs by creation date
     * @param date as String in format yyyy-MM-dd
     * @return a list of jobs
     */
    public List<Job> getJobsByDate(String date) {
        return jobRepository.findJobsByDate(LocalDate.parse(date));
    }

    /**
     * Get a list of {@link Helper} who matcher with the job criteria
     * @param id job identifier
     * @return a list of {@link Helper}
     */
    public List<Helper> getPotentialHelpersForJob(long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        return jobMatcherService.getPotentialHelpersForJob(job.get());
    }

    /**
     * Persist a job
     * @param job the {@link Job}
     */
    public void createJob(Job job) {
        Helpseeker helpseeker = userRepository.findHelpseekerByEmail(job.getAuthor().getEmail());
        job.setAuthor(helpseeker);
        job.setStatus(JobStatus.OPEN);
        job.setCreated(LocalDate.now());
        jobRepository.save(job);
    }

    /**
     * Update a job
     * @param job the {@link Job}
     */
    public void updateJob(Job job) {
        if (jobRepository.findById(job.getId()).isEmpty()) throw new RecordNotFoundException(String.valueOf(job.getId()));
        jobRepository.save(job);
    }

    /**
     * Add a {@link Helper} to a {@link Job}
     * @param id job identifier
     * @param email helper identifier
     * @return the updated {@link Job}
     */
    public Job addHelperForJob(long id, String email) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Helper helper = userRepository.findHelperByEmail(email);
        Job job = optionalJob.get();
        job.setMatchedHelper(helper);
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.save(job);
        return job;
    }

    /**
     * Delete a job
     * @param id job identifier
     */
    public void deleteJobById(long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Job job = optionalJob.get();
        jobRepository.delete(job);
    }
}

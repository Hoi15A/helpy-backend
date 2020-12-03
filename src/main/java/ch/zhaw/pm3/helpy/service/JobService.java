package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.config.AppConfig;
import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.matcher.MatcherController;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.job.JobDTO;
import ch.zhaw.pm3.helpy.model.job.JobStatus;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserDTO;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

import static ch.zhaw.pm3.helpy.service.util.DTOMapper.*;

/**
 * Service for the jobs.
 * @author meletela
 * @version 26.10.2020
 */
@RequiredArgsConstructor
@Transactional
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final MatcherController matcherController;

    /**
     * Get all jobs from persistence.
     * @return a set of jobs
     */
    public Set<JobDTO> getAllJobs() {
        return mapJobsToDTOs(new HashSet<>(jobRepository.findAll()));
    }

    /**
     * Get a job by identifier
     * @param id job identifier
     * @return the job or {@link RecordNotFoundException} when the job is not existent
     */
    public JobDTO getJobById(long id) {
        Optional<Job> job = jobRepository.findById(id);
        if(job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        return mapJobToDTO(job.get());
    }

    /**
     * Get a list of jobs by status
     * @param status the {@link JobStatus} to search in the jobs
     * @return a set of jobs
     */
    public Set<JobDTO> getJobsByStatus(JobStatus status) {
        return mapJobsToDTOs(jobRepository.findJobsByStatus(status));
    }

    /**
     * Get a list of jobs by author
     * @param email author identifier
     * @return a set of jobs
     */
    public Set<JobDTO> getJobsByAuthor(String email) {
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()) throw new RecordNotFoundException(email);
        return mapJobsToDTOs(jobRepository.findJobsByAuthor(user.get()));
    }

    /**
     * Get a list of jobs by matchedHelper
     * @param email author identifier
     * @return a set of jobs
     */
    public Set<JobDTO> getJobsByMatchedHelper(String email) {
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()) throw new RecordNotFoundException(email);
        return mapJobsToDTOs(jobRepository.findJobsByMatchedHelper(user.get()));
    }

    /**
     * Get a list of jobs by {@link Category}
     * @param category the {@link Category} to search in the jobs as String
     * @return a set of jobs
     */
    public Set<JobDTO> getJobsByCategory(String category) {
        return mapJobsToDTOs(jobRepository.findJobsByCategory(category));
    }

    /**
     * Get a set of jobs containing elements from a list of {@link Category}
     * @param categories list of {@link Category} as array
     * @return a set of jobs
     */
    public Set<JobDTO> getJobsByCategories(Category[] categories) {
        Set<Job> jobs = new HashSet<>();
        for (Category category : categories) {
            jobs.addAll(jobRepository.findJobsByCategory(category.getName()));
        }
        return mapJobsToDTOs(jobs);
    }

    /**
     * Get a list of jobs by {@link Tag}
     * @param tag the {@link Tag} to search in the jobs as String
     * @return a set of jobs
     */
    public Set<JobDTO> getJobsByTag(String tag) {
        return mapJobsToDTOs(jobRepository.findJobsByTag(tag));
    }

    /**
     * Get a set of jobs containing elements from a list of {@link Tag}
     * @param tags list of {@link Tag} as array
     * @return a list of jobs
     */
    public Set<JobDTO> getJobsByTags(Tag[] tags) {
        Set<Job> jobs = new HashSet<>();
        for (Tag tag : tags) {
            jobs.addAll(jobRepository.findJobsByTag(tag.getName()));
        }
        return mapJobsToDTOs(jobs);
    }

    /**
     * Get a list of jobs by creation date
     * @param date as String in format yyyy-MM-dd
     * @return a set of jobs
     */
    public Set<JobDTO> getJobsByDate(String date) {
        return mapJobsToDTOs(jobRepository.findJobsByDate(LocalDate.parse(date)));
    }

    /**
     * Get a list of {@link UserDTO} who matcher with the job criteria
     * @param id job identifier
     * @return a list of {@link UserDTO}
     */
    public List<UserDTO> getPotentialHelpersForJob(long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        return mapUsersToDTOs(new ArrayList<>(matcherController.getPotentialMatches(job.get())));
    }

    /**
     * Persist a job
     * @param dto the {@link JobDTO}
     */
    public JobDTO createJob(JobDTO dto) {
        Job job = mapDTOToJob(dto);
        Optional<User> user = userRepository.findById(job.getAuthor().getEmail());
        String message = String.format("Unable to find user with mail address: %s", job.getAuthor().getEmail());
        if (user.isEmpty()) throw new RecordNotFoundException(message);
        job.setAuthor(user.get());
        job.setStatus(JobStatus.OPEN);
        job.setCreated(LocalDate.now());
        jobRepository.save(job);
        return mapJobToDTO(job);
    }

    /**
     * Update a job
     * @param id the job identifier
     * @param dto the {@link JobDTO}
     */
    public void updateJob(long id, JobDTO dto) {
        Optional<Job> oldOptional = jobRepository.findById(id);
        if (oldOptional.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Job oldJob = oldOptional.get();
        Job job = mapDTOToJob(dto);
        job.setId(id);
        job.setCreated(oldJob.getCreated());
        job.setStatus(oldJob.getStatus());
        jobRepository.save(job);
    }

    /**
     * Close a job
     * @param id job identifier
     * @return closedJob
     */
    public JobDTO closeJob(long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Job job = optionalJob.get();
        job.setStatus(JobStatus.CLOSED);
        jobRepository.save(job);
        return mapJobToDTO(job);
    }

    /**
     * Add a {@link User} to a {@link Job}
     * @param id job identifier
     * @param email helper identifier
     * @return the updated {@link Job}
     */
    public JobDTO addHelperForJob(long id, String email) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()) throw new RecordNotFoundException("Der gesuchte Helfer konnte nicht im System gefunden werden,");
        Job job = optionalJob.get();
        job.setMatchedHelper(user.get());
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.save(job);
        return mapJobToDTO(job);
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

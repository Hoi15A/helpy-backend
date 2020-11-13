package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.job.JobDTO;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserDTO;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ch.zhaw.pm3.helpy.service.DTOMapper.*;

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
    private final JobMatcherService jobMatcherService;
    private final UserRepository userRepository;

    /**
     * Get all jobs from persistence.
     * @return a list of jobs
     */
    public List<JobDTO> getAllJobs() {
        return mapJobsToDTOs(jobRepository.findAll());
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
     * @return a list of jobs
     */
    public List<JobDTO> getJobsByStatus(JobStatus status) {
        return mapJobsToDTOs(jobRepository.findJobsByStatus(status));
    }

    /**
     * Get a list of jobs by author
     * @param email author identifier
     * @return a list of jobs
     */
    public List<JobDTO> getJobsByAuthor(String email) {
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()) throw new RecordNotFoundException(email);
        return mapJobsToDTOs(jobRepository.findJobsByAuthor(user.get()));
    }

    /**
     * Get a list of jobs by matchedHelper
     * @param email author identifier
     * @return a list of jobs
     */
    public List<JobDTO> getJobsByMatchedHelper(String email) {
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()) throw new RecordNotFoundException(email);
        return mapJobsToDTOs(jobRepository.findJobsByMatchedHelper(user.get()));
    }

    /**
     * Get a list of jobs by {@link Category}
     * @param category the {@link Category} to search in the jobs as String
     * @return a list of jobs
     */
    public List<JobDTO> getJobsByCategory(String category) {
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
     * @return a list of jobs
     */
    public List<JobDTO> getJobsByTag(String tag) {
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
     * @return a list of jobs
     */
    public List<JobDTO> getJobsByDate(String date) {
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
        return mapUsersToDTOs(jobMatcherService.getPotentialHelpersForJob(job.get()));
    }

    /**
     * Persist a job
     * @param dto the {@link JobDTO}
     */
    public JobDTO createJob(JobDTO dto) {
        Job job = mapDTOToJob(dto);
        Optional<User> user = userRepository.findById(job.getAuthor().getEmail());
        job.setAuthor(user.get());
        job.setStatus(JobStatus.OPEN);
        job.setCreated(LocalDate.now());
        jobRepository.save(job);
        return mapJobToDTO(job);
    }

    /**
     * Update a job
     * @param dto the {@link JobDTO}
     */
    public void updateJob(long id, JobDTO dto) {
        if (jobRepository.findById(id).isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Job job = mapDTOToJob(dto);
        job.setId(id);
        jobRepository.save(job);
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

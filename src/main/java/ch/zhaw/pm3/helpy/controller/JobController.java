package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.*;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * This class is for the manipulation of the {@link Job} model.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/job")
public class JobController {

    private final JobService jobService;

    /**
     * Returns all jobs from the database as json.
     * @return ResponseEntity<List<JobDTO>>
     */
    @GetMapping("all")
    public ResponseEntity<List<JobDTO>> getJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    /**
     * Takes a {@link Job} and saves it in the database.
     * The same {@link Job} will be returned after creation.
     * @param job {@link Job}
     * @return ResponseEntity<Job>
     */
    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody final JobDTO job) {
        jobService.createJob(job);
        return ResponseEntity.ok(job);
    }

    /**
     * Takes a job id and deletes the object from the database.
     * @param id long
     * @return ResponseEntity<Object>
     */
    @DeleteMapping("remove/{id}")
    public ResponseEntity<Object> removeJob(@PathVariable("id") final long id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Takes a {@link JobDTO} and updates the database entry with the same id.
     * The saved {@link JobDTO} will be returned after the update.
     * @param job {@link JobDTO}
     * @return ResponseEntity<JobDTO>
     */
    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobDTO> updateJob(@Valid @RequestBody final JobDTO job) {
        jobService.updateJob(job);
        return ResponseEntity.ok(job);
    }

    /**
     * Takes a job id and returns the found {@link JobDTO} object from the database.
     * @param id long
     * @return ResponseEntity<JobDTO>
     */
    @GetMapping("id/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable("id") final long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    /**
     * Takes a job id and returns a list of potential helpers.
     * @param id long
     * @return ResponseEntity<List<User>>
     */
    @GetMapping("id/{id}/find-helper")
    public ResponseEntity<List<User>> findPotentialHelper(@PathVariable("id") final long id) {
        return ResponseEntity.ok(jobService.getPotentialHelpersForJob(id));
    }

    /**
     * Takes a job id as well as an helper email and adds it to the job.
     * @param id long
     * @param email string
     * @return ResponseEntity<JobDTO>
     */
    @PutMapping("id/{id}/set-helper/{mail}")
    public ResponseEntity<JobDTO> updateHelper(@PathVariable("id") final long id,
                                                     @PathVariable("mail") final String email) {
        return ResponseEntity.ok(jobService.addHelperForJob(id, email));
    }

    /**
     * Takes a job status and returns all jobs with the according status.
     * @param status {@link JobStatus}
     * @return ResponseEntity<List<JobDTO>>
     */
    @GetMapping("status/{status}")
    public ResponseEntity<List<JobDTO>> getJobsByStatus(@PathVariable("status") final JobStatus status) {
        return ResponseEntity.ok(jobService.getJobsByStatus(status));
    }

    /**
     * Takes an email from the author and returns a list of jobs from the same author.
     * @param email string
     * @return ResponseEntity<List<JobDTO>>
     */
    @GetMapping("author/{author}")
    public ResponseEntity<List<JobDTO>> getJobsByAuthor(@PathVariable("author") final String email) {
        return ResponseEntity.ok(jobService.getJobsByAuthor(email));
    }

    /**
     * Takes the email from the matched helper and returns a list of jobs where this helper is matched with.
     * @param email string
     * @return ResponseEntity<List<JobDTO>>
     */
    @GetMapping("helper/{matchedHelper}")
    public ResponseEntity<List<JobDTO>> getJobsByMatchedHelper(@PathVariable("matchedHelper") final String email) {
        return ResponseEntity.ok(jobService.getJobsByMatchedHelper(email));
    }

    /**
     * Takes a category name and returns all jobs with the provided category name.
     * @param category string
     * @return ResponseEntity<List<JobDTO>>
     */
    @GetMapping("category/{category}")
    public ResponseEntity<List<JobDTO>> getJobsByCategory(@PathVariable("category") final String category) {
        return ResponseEntity.ok(jobService.getJobsByCategory(category));
    }

    /**
     * Takes a {@link Category} array and returns all jobs with the provided categories.
     * @param categories {@link Category}[]
     * @return ResponseEntity<Set<JobDTO>>
     */
    @PostMapping("categories")
    public ResponseEntity<Set<JobDTO>> getJobsByCategories(@Valid @RequestBody final Category[] categories) {
        return ResponseEntity.ok(jobService.getJobsByCategories(categories));
    }

    /**
     * Takes a tag name and returns all the jobs with the provided tag name.
     * @param tag string
     * @return ResponseEntity<List<JobDTO>>
     */
    @GetMapping("tag/{tag}")
    public ResponseEntity<List<JobDTO>> getJobsByTag(@PathVariable("tag") final String tag) {
        return ResponseEntity.ok(jobService.getJobsByTag(tag));
    }

    /**
     * Takes a {@link Tag} array and returns all the Jobs with the provided tags.
     * @param tags {@link Tag}[]
     * @return ResponseEntity<Set<JobDTO>>
     */
    @PostMapping("tags")
    public ResponseEntity<Set<JobDTO>> getJobsByTags(@Valid @RequestBody final Tag[] tags) {
        return ResponseEntity.ok(jobService.getJobsByTags(tags));
    }

    /**
     * Takes a date and returns all jobs with the same creation date.
     * @param date string
     * @return ResponseEntity<List<JobDTO>>
     */
    @GetMapping("date/{date}")
    public ResponseEntity<List<JobDTO>> getJobsByDate(@PathVariable("date") final String date) {
        return ResponseEntity.ok(jobService.getJobsByDate(date));
    }
}

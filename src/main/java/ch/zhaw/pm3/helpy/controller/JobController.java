package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.model.*;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.service.JobService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Job>> getJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Job> createJob(@Valid @RequestBody final Job job) {
        jobService.createJob(job);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<Object> removeJob(@PathVariable("id") final long id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Job> updateJob(@Valid @RequestBody final Job job) {
        jobService.updateJob(job);
        return ResponseEntity.ok(job);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") final long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("id/{id}/find-helper")
    public ResponseEntity<List<Helper>> findPotentialHelper(@PathVariable("id") final long id) {
        return ResponseEntity.ok(jobService.getPotentialHelpersForJob(id));
    }

    @PutMapping("id/{id}/set-helper/{mail}")
    public ResponseEntity<Job> updateHelper(@PathVariable("id") final long id,
                                                     @PathVariable("mail") final String email) {
        return ResponseEntity.ok(jobService.addHelperForJob(id, email));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable("status") final JobStatus status) {
        return ResponseEntity.ok(jobService.getJobsByStatus(status));
    }

    @GetMapping("author/{author}")
    public ResponseEntity<List<Job>> getJobsByAuthor(@PathVariable("author") final String email) {
        return ResponseEntity.ok(jobService.getJobsByAuthor(email));
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Job>> getJobsByCategory(@PathVariable("category") final String category) {
        return ResponseEntity.ok(jobService.getJobsByCategory(category));
    }

    @PostMapping("categories")
    public ResponseEntity<Set<Job>> getJobsByCategories(@Valid @RequestBody final Category[] categories) {
        return ResponseEntity.ok(jobService.getJobsByCategories(categories));
    }

    @GetMapping("tag/{tag}")
    public ResponseEntity<List<Job>> getJobsByTag(@PathVariable("tag") final String tag) {
        return ResponseEntity.ok(jobService.getJobsByTag(tag));
    }

    @PostMapping("tags")
    public ResponseEntity<Set<Job>> getJobsByTags(@Valid @RequestBody final Tag[] tags) {
        return ResponseEntity.ok(jobService.getJobsByTags(tags));
    }

    @GetMapping("date/{date}")
    public ResponseEntity<List<Job>> getJobsByDate(@PathVariable("date") final String date) {
        return ResponseEntity.ok(jobService.getJobsByDate(date));
    }
}

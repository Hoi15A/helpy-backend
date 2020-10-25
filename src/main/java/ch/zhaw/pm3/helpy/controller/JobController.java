package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.constant.JobStatus;
import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.matcher.JobMatcher;
import ch.zhaw.pm3.helpy.model.*;
import ch.zhaw.pm3.helpy.model.category.Category;
import ch.zhaw.pm3.helpy.model.category.Tag;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("api/job")
public class JobController {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobMatcher matcher;

    @GetMapping("all")
    public ResponseEntity<List<Job>> getJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Job> createJob(@Valid @RequestBody final Job job) {
        Helpseeker helpseeker = userRepository.findHelpseekerByName(job.getAuthor().getEmail());
        job.setAuthor(helpseeker);
        job.setStatus(JobStatus.OPEN);
        job.setCreated(LocalDate.now());
        jobRepository.save(job);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<Object> removeJob(@PathVariable("id") final long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Helpseeker helpseeker = job.get().getAuthor();
        helpseeker.getTasks().remove(job.get());
        Helper helper = job.get().getMatchedHelper();
        if (helper != null) {
            helper.getCompletedJobs().remove(job.get());
            userRepository.save(helper);
        }
        job.get().setMatchedHelper(null);
        job.get().setAuthor(null);
        jobRepository.save(job.get());
        userRepository.save(helpseeker);
        jobRepository.delete(job.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Job> updateJob(@Valid @RequestBody final Job job) {
        if (jobRepository.findById(job.getId()).isEmpty()) throw new RecordNotFoundException(String.valueOf(job.getId()));
        jobRepository.save(job);
        return ResponseEntity.ok(job);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") final long id) {
        Optional<Job> job = jobRepository.findById(id);
        if(job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        return ResponseEntity.ok(job.get());
    }

    @GetMapping("id/{id}/find-helper")
    public ResponseEntity<List<Helper>> findPotentialHelper(@PathVariable("id") final long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        matcher.setJob(job.get());
        return ResponseEntity.ok(matcher.getPotentialHelpers());
    }

    @PutMapping("id/{id}/set-helper/{mail}")
    public ResponseEntity<Job> updateHelper(@PathVariable("id") final long id,
                                                     @PathVariable("mail") final String email) {
        Optional<Job> job = jobRepository.findById(id);
        Helper helper = userRepository.findHelperByName(email);
        if (job.isEmpty()) throw new RecordNotFoundException(String.valueOf(id));
        Job j = job.get();
        j.setMatchedHelper(helper);
        j.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.save(j);
        return ResponseEntity.ok(j);
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable("status") final JobStatus status) {
        return ResponseEntity.ok(jobRepository.findJobsByStatus(status));
    }

    @GetMapping("author/{author}")
    public ResponseEntity<List<Job>> getJobsByAuthor(@PathVariable("author") final String name) {
        Helpseeker helpseeker = userRepository.findHelpseekerByName(name);
        if (helpseeker == null) throw new RecordNotFoundException(name);
        return ResponseEntity.ok(jobRepository.findJobsByAuthor(helpseeker));
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Job>> getJobsByCategory(@PathVariable("category") final String category) {
        return ResponseEntity.ok(jobRepository.findJobsByCategory(category));
    }

    @PostMapping("categories")
    public ResponseEntity<Set<Job>> getJobsByCategories(@Valid @RequestBody final Category[] categories) {
        Set<Job> jobs = new HashSet<>();
        for (Category category : categories) {
            jobs.addAll(jobRepository.findJobsByCategory(category.getName()));
        }
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("tag/{tag}")
    public ResponseEntity<List<Job>> getJobsByTag(@PathVariable("tag") final String tag) {
        return ResponseEntity.ok(jobRepository.findJobsByTag(tag));
    }

    @PostMapping("tags")
    public ResponseEntity<Set<Job>> getJobsByTags(@Valid @RequestBody final Tag[] tags) {
        Set<Job> jobs = new HashSet<>();
        for (Tag tag : tags) {
            jobs.addAll(jobRepository.findJobsByTag(tag.getName()));
        }
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("date/{date}")
    public ResponseEntity<List<Job>> getJobsByDate(@PathVariable("date") final String date) {
        return ResponseEntity.ok(jobRepository.findJobsByDate(LocalDate.parse(date)));
    }
}

package ch.zhaw.pm3.helpy.controller;

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
        jobRepository.save(job);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<Job> removeJob(@PathVariable("id") final long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) return ResponseEntity.notFound().build();
        jobRepository.delete(job.get());
        return ResponseEntity.ok(job.get());
    }

    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Job> updateJob(@Valid @RequestBody final Job job) {
        if (jobRepository.findById(job.getId()).isEmpty()) return ResponseEntity.notFound().build();
        jobRepository.save(job);
        return ResponseEntity.ok(job);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") final long id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(job.get());
    }

    @GetMapping("matches/{id}")
    public ResponseEntity<List<Helper>> getMatchesByJobId(@PathVariable("id") final long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) return ResponseEntity.notFound().build();
        matcher.setJob(job.get());
        return ResponseEntity.ok(matcher.getPotentialHelper());
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable("status") final String status) {
        return ResponseEntity.ok(jobRepository.findJobsByStatus(status));
    }

    @GetMapping("author/{author}")
    public ResponseEntity<List<Job>> getJobsByAuthor(@PathVariable("author") final String name) {
        Helpseeker helpseeker = userRepository.findHelpseekerByName(name);
        return helpseeker == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(jobRepository.findJobsByAuthor(helpseeker));
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
        return ResponseEntity.ok(jobRepository.findJobsByDate(date));
    }
}

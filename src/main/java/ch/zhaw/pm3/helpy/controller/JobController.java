package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.Category;
import ch.zhaw.pm3.helpy.model.Helpseeker;
import ch.zhaw.pm3.helpy.model.Job;
import ch.zhaw.pm3.helpy.model.Tag;
import ch.zhaw.pm3.helpy.repository.CategoryRepository;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/job")
public class JobController {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    private final Gson gson = new Gson();

    @GetMapping("all")
    public ResponseEntity<List<Job>> getJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @PostMapping("add")
    public ResponseEntity<Job> createJob(@RequestBody final String body) {
        Job job = gson.fromJson(body, Job.class);
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

    @PutMapping("update")
    public ResponseEntity<Job> updateJob(@RequestBody final String body) {
        Job job = gson.fromJson(body, Job.class);
        if (jobRepository.findById(job.getId()).isEmpty()) return ResponseEntity.notFound().build();
        jobRepository.save(job);
        return ResponseEntity.ok(job);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") final long id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(job.get());
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable("status") final String status) {
        return ResponseEntity.badRequest().body(jobRepository.findJobsByStatus(status));
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
    public ResponseEntity<Set<Job>> getJobsByCategories(@RequestBody final String categories) {
        Category[] cats = gson.fromJson(categories, Category[].class);
        Set<Job> jobs = new HashSet<>();
        for (Category c : cats) {
            jobs.addAll(jobRepository.findJobsByCategory(c.getName()));
        }
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("tag/{tag}")
    public ResponseEntity<List<Job>> getJobsByTag(@PathVariable("tag") final String tag) {
        return ResponseEntity.ok(jobRepository.findJobsByTag(tag));
    }

    @PostMapping("tags")
    public ResponseEntity<Set<Job>> getJobsByTags(@RequestBody final String tags) {
        Tag[] tagArray = gson.fromJson(tags, Tag[].class);
        Set<Job> jobs = new HashSet<>();
        for (Tag t : tagArray) {
            jobs.addAll(jobRepository.findJobsByTag(t.getName()));
        }
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("date/{date}")
    public ResponseEntity<List<Job>> getJobsByDate(@PathVariable("date") final String date) {
        return ResponseEntity.ok(jobRepository.findJobsByDate(date));
    }
}

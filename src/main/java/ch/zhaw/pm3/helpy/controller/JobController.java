package ch.zhaw.pm3.helpy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/job")
public class JobController {

    @GetMapping("all")
    public ResponseEntity<List<String>> getJobs() {
        //todo
        List<String> jobs = new ArrayList<>();
        jobs.add("Help Me");
        return ResponseEntity.badRequest().body(jobs);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<String> getJobById(@PathVariable("id") final String id) {
        //todo
        return ResponseEntity.badRequest().body("Not Implemented");
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<String>> getJobsByStatus(@PathVariable("status") final String status) {
        //todo
        List<String> jobs = new ArrayList<>();
        jobs.add("Help Me");
        return ResponseEntity.badRequest().body(jobs);
    }

    @GetMapping("author/{author}")
    public ResponseEntity<List<String>> getJobsByAuthor(@PathVariable("author") final String author) {
        //todo
        List<String> jobs = new ArrayList<>();
        jobs.add("Help Me");
        return ResponseEntity.badRequest().body(jobs);
    }



}

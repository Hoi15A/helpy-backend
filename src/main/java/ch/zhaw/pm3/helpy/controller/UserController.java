package ch.zhaw.pm3.helpy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping("{username}")
    public ResponseEntity<String> getUser(@PathVariable("username") final String username) {
        //todo
        return ResponseEntity.ok("user");
    }

    @PostMapping("add")
    public ResponseEntity<String> addUser(@RequestBody String body) {
        //todo
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("remove/{username}")
    public ResponseEntity<String> removeUser(@PathVariable("username") final String username) {
        //todo
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{username}")
    public ResponseEntity<String> updateUser(@PathVariable("username") final String username) {
        //todo
        return ResponseEntity.ok().build();
    }

    @GetMapping("city/{city}")
    public ResponseEntity<List<String>> getByCity(@PathVariable("city") final String city) {
        //todo
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<String>> getByStatus(@PathVariable("status") final String status) {
        //todo
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("age/{age}")
    public ResponseEntity<List<String>> getByAge(@PathVariable("age") final int age) {
        //todo
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("rating/{rating}")
    public ResponseEntity<List<String>> getByRating(@PathVariable("rating") final String rating) {
        //todo
        return ResponseEntity.ok(new ArrayList<>());
    }
}

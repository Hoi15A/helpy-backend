package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.Helper;
import ch.zhaw.pm3.helpy.model.Helpseeker;
import ch.zhaw.pm3.helpy.model.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") final String username) {
        User user = userRepository.findUserByName(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @GetMapping("all")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@Valid @RequestBody final User user) {
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "addhelpseeker", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addHelpseeker(@Valid @RequestBody final Helpseeker helpseeker) {
        userRepository.save(helpseeker);
        return ResponseEntity.ok(helpseeker);
    }

    @PostMapping(value = "addhelper", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addHelper(@Valid @RequestBody final Helper helper) {
        userRepository.save(helper);
        return ResponseEntity.ok(helper);
    }

    @DeleteMapping("remove/{username}")
    public ResponseEntity<User> removeUser(@PathVariable("username") final String username) {
        User user = userRepository.findUserByName(username);
        if (user == null) { return ResponseEntity.notFound().build(); }
        userRepository.delete(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("update/{username}")
    public ResponseEntity<User> updateUser(@RequestBody final User userUpdate,
                                           @PathVariable("username") final String username) {
        User user = userRepository.findUserByName(username);
        if (user == null) return ResponseEntity.notFound().build();
        userRepository.delete(user);
        userRepository.save(userUpdate);
        return ResponseEntity.ok(user);
    }

    @GetMapping("plz/{plz}")
    public ResponseEntity<List<User>> getByPlz(@PathVariable("plz") final int plz) {
        return ResponseEntity.ok(userRepository.findUsersByPlz(plz));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<User>> getByStatus(@PathVariable("status") final String status) {
        return ResponseEntity.ok(userRepository.findUsersByStatus(status));
    }

    @GetMapping("rating/{rating}")
    public ResponseEntity<List<Helper>> getByRating(@PathVariable("rating") final int rating) {
        return ResponseEntity.ok(userRepository.findUsersByRating(rating));
    }
}

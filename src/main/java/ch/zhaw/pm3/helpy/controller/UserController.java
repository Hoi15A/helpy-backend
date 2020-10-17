package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final Gson gson = new Gson();

    @Autowired
    UserRepository userRepository;

    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") final String username) {
        User user = userRepository.findUserByName(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody String body) {
        User user = gson.fromJson(body, User.class);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("remove/{username}")
    public ResponseEntity<User> removeUser(@PathVariable("username") final String username) {
        User user = userRepository.findUserByName(username);
        if (user == null) { return ResponseEntity.notFound().build(); }
        userRepository.delete(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody final String username) {
        //todo
        return ResponseEntity.ok().build();
    }

    @GetMapping("plz/{plz}")
    public ResponseEntity<List<User>> getByPlz(@PathVariable("plz") final int plz) {
        return ResponseEntity.ok(userRepository.findUsersByPlz(plz));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<User>> getByStatus(@PathVariable("status") final String status) {
        return ResponseEntity.ok(userRepository.findUsersByStatus(status));
    }

    @GetMapping("age/{age}")
    public ResponseEntity<List<String>> getByAge(@PathVariable("age") final int age) {
        //todo
        return ResponseEntity.ok().build();
    }

    @GetMapping("rating/{rating}")
    public ResponseEntity<List<User>> getByRating(@PathVariable("rating") final int rating) {
        return ResponseEntity.ok(userRepository.findUsersByRating(rating));
    }
}

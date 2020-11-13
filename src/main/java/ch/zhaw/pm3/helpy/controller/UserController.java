package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This class is for the manipulation of the {@link User} model.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    /**
     * Takes an email address and returns the {@link User} with
     * the same email address from the database.
     * @param username string
     * @return ResponseEntity<User>
     */
    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.findByEmail(username));
    }

    /**
     * Returns all {@link User} from the database.
     * @return ResponseEntity<List<User>>
     */
    @GetMapping("all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Takes a {@link User} and saves it to the database.
     * The same {@link User} will be returned after creation.
     * @param user {@link User}
     * @return ResponseEntity<User>
     */
    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@Valid @RequestBody final User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    /**
     * Takes an email address and deletes the user with
     * the same email address from the database.
     * @param username string
     * @return ResponseEntity<User>
     */
    @DeleteMapping("remove/{username}")
    public ResponseEntity<User> removeUser(@PathVariable("username") final String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    /**
     * Takes a {@link User} as well as an email address.
     * Updates the user with the provided email address.
     * @param userUpdate {@link User}
     * @param username string
     * @return ResponseEntity<User>
     */
    @PutMapping("update/{username}")
    public ResponseEntity<User> updateUser(@RequestBody final User userUpdate,
                                           @PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.updateUser(username, userUpdate));
    }

    /**
     * Takes an Integer as well as an email address.
     * Adds the given Integer to the list of ratings
     * of the {@link User} with the provided email address.
     * @param rating Integer to add to ratings list
     * @param username string to match with {@link User} email
     * @return ResponseEntity<User>
     */
    @PostMapping("addRating/{username}")
    public ResponseEntity<User> addRating(@RequestBody final int rating, @PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.addRating(username, rating));
    }
}

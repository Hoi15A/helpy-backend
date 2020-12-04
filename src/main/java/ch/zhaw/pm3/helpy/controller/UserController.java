package ch.zhaw.pm3.helpy.controller;

import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserDTO;
import ch.zhaw.pm3.helpy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Takes an email address and returns the {@link UserDTO} with
     * the same email address from the database.
     * @param username string
     * @return ResponseEntity<UserDTO>
     */
    @GetMapping("{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.findByEmail(username));
    }

    /**
     * Returns all {@link UserDTO} from the database.
     * @return ResponseEntity<List<UserDTO>>
     */
    @GetMapping("all")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Takes a {@link UserDTO} and saves it to the database.
     * The same {@link UserDTO} will be returned after creation.
     * @param user {@link UserDTO}
     * @return ResponseEntity<User>
     */
    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody final UserDTO user) {
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
     * Takes a {@link UserDTO} as well as an email address.
     * Updates the user with the provided email address.
     * @param userUpdate {@link UserDTO}
     * @param username string
     * @return ResponseEntity<User>
     */
    @PutMapping("update/{username}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody final UserDTO userUpdate,
                                           @PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.updateUser(username, userUpdate));
    }

    /**
     * Takes an Integer as well as an email address.
     * Adds the given Integer to the list of ratings
     * of the {@link UserDTO} with the provided email address.
     * @param rating Integer to add to ratings list
     * @param username string to match with {@link UserDTO} email
     * @return ResponseEntity<UserDTO>
     */
    @PostMapping("addRating/{username}")
    public ResponseEntity<UserDTO> addRating(@RequestBody final int rating, @PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.addRating(username, rating));
    }

    @GetMapping("rating/latest/{username}")
    public ResponseEntity<Integer> getLatestRating(@PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.getLatestRating(username));
    }

    @GetMapping("points/{username}")
    public ResponseEntity<Integer> getPoints(@PathVariable("username") final String username) {
        return ResponseEntity.ok(userService.getPoints(username));
    }

    @GetMapping("points/top-ten")
    public ResponseEntity<List<User>> getTopTenUser() {
        return ResponseEntity.ok(userService.getTopTenUser());
    }
}

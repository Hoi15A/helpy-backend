package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.exception.RecordAlreadyExistsException;
import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.UserDTO;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ch.zhaw.pm3.helpy.service.DTOMapper.*;

/**
 * Service for the users.
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Return a user by email (id)
     * @param email his email address
     * @return the {@link UserDTO}
     */
    public UserDTO findByEmail(String email) {
        User user = getExistingUser(email);
        return mapUserToDTO(user);
    }

    /**
     * Returns all users.
     * @return list of {@link UserDTO}
     */
    public List<UserDTO> getAllUsers() {
        return mapUsersToDTOs(userRepository.findAll());
    }

    /**
     * Creates a {@link UserDTO}
     * @param dto the new {@link UserDTO}
     * @return the new {@link UserDTO}
     */
    public UserDTO createUser(UserDTO dto) {
        User user = mapDTOToUser(dto);
        Optional<User> optionalUser = userRepository.findById(user.getEmail());
        if (optionalUser.isPresent()) {
            String message = String.format("User with the id: %s already exists", user.getEmail());
            throw new RecordAlreadyExistsException(message);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return mapUserToDTO(user);
    }

    /**
     * Deletes a user by email
     * @param email his email
     */
    public void deleteUser(String email) {
        User user = getExistingUser(email);
        jobRepository.removeHelperFromJob(email);
        jobRepository.removeAuthorFromJob(email);
        userRepository.delete(user);
    }

    /**
     * Updates a user by email
     * @param email his old email
     * @param userDTO the updated {@link UserDTO}
     * @return the updated {@link UserDTO}
     */
    public UserDTO updateUser(String email, UserDTO userDTO) {
        User user = mapDTOToUser(userDTO);
        User existingUser = getExistingUser(email);
        if (!existingUser.getEmail().equals(user.getEmail())) {
            userRepository.updateUserEmail(existingUser.getEmail(), user.getEmail());
        }
        userRepository.save(user);
        return mapUserToDTO(user);
    }

    /**
     * Add a rating to the {@link User}
     * @param email of {@link User} to add rating
     * @param rating to add to user
     * @return the updated {@link User}
     */
    public UserDTO addRating(String email, int rating) {
        if (rating > 10 || rating < 0) {
            String message = String.format("Rating \"%s\" is out of bounds of range 0 to 10", rating);
            throw new IllegalArgumentException(message);
        }
        User user = getExistingUser(email);
        user.getRatings().add(rating);
        userRepository.save(user);
        return mapUserToDTO(user);
    }

    public int getLatestRating(String email) {
        User user = getExistingUser(email);
        List<Integer> ratings = user.getRatings();
        return ratings.size() > 0 ? ratings.get(ratings.size() - 1) : -1;
    }

    public int getPoints(String email) {
        User user = getExistingUser(email);
        return user.getRatings().stream().reduce(0, Integer::sum);
    }

    private User getExistingUser(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        if (optionalUser.isEmpty()) {
            String message = String.format("Could not find user with the mail address: %s", email);
            throw new RecordNotFoundException(message);
        }
        return optionalUser.get();
    }

    public List<User> getTopTenUser() {

        Comparator<User> comparator = (o1, o2) -> {
            int u1 = o1.getRatings().stream().mapToInt(Integer::valueOf).sum();
            int u2 = o2.getRatings().stream().mapToInt(Integer::valueOf).sum();
            return u2 - u1;
        };

        return userRepository.findAll().stream()
                .sorted(comparator)
                .limit(10)
                .collect(Collectors.toList());
    }
}

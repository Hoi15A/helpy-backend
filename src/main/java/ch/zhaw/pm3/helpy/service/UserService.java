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

import java.util.List;
import java.util.Optional;

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
        Optional<User> user = userRepository.findById(email);
        if(user.isEmpty()) throw new RecordNotFoundException(email);
        return mapUserToDTO(user.get());
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
        if (userRepository.existsByEmail(user.getEmail()) > 0) {
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
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()) throw new RecordNotFoundException(email);
        jobRepository.removeHelperFromJob(email);
        jobRepository.removeAuthorFromJob(email);
        userRepository.delete(user.get());
    }

    /**
     * Upates a user by email
     * @param email his old email
     * @param userDTO the updated {@link UserDTO}
     * @return the updated {@link UserDTO}
     */
    public UserDTO updateUser(String email, UserDTO userDTO) {
        User user = mapDTOToUser(userDTO);
        if (userRepository.existsByEmail(email) < 1) {
            String message = String.format("Could not find user with the mail address: %s", email);
            throw new RecordNotFoundException(message);
        }
        if (!user.getEmail().equals(email)) {
            userRepository.updateUserEmail(email, user.getEmail());
            Optional<User> optional = userRepository.findById(user.getEmail());
            user = optional.get();
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
        if (userRepository.existsByEmail(email) < 1) {
            String message = String.format("Could not find user with the mail address: %s", email);
            throw new RecordNotFoundException(message);
        }
        if (rating > 10 || rating < 0) {
            String message = String.format("Rating \"%s\" is out of bounds of range 0 to 10", rating);
            throw new IllegalArgumentException(message);
        }
        Optional<User> optional = userRepository.findById(email);
        if (optional.isPresent()) {
            User user = optional.get();
            List<Integer> list = user.getRatings();
            list.add(rating);
            user.setRatings(list);
            userRepository.save(user);
            return mapUserToDTO(user);
        }
        return null;
    }
}

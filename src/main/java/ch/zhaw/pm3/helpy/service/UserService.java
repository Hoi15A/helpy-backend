package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.exception.RecordAlreadyExistsException;
import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service for the users.
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    /**
     * Return a user by email (id)
     * @param email his email address
     * @return the {@link User}
     */
    public User findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) throw new RecordNotFoundException(email);
        return user;
    }

    /**
     * Returns all users.
     * @return list of {@link User}
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a {@link User}
     * @param user the new {@link User}
     * @return the new {@link User}
     */
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()) > 0) {
            String message = String.format("User with the id: %s already exists", user.getEmail());
            throw new RecordAlreadyExistsException(message);
        }
        userRepository.save(user);
        return user;
    }

    /**
     * Deletes a user by email
     * @param email his email
     */
    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) throw new RecordNotFoundException(email);
        if (user instanceof Helper) {
            jobRepository.removeHelperFromJob(email);
        }
        else if (user instanceof Helpseeker) {
            jobRepository.removeAuthorFromJob(email);
        }
        userRepository.delete(user);
    }

    /**
     * Upates a user by email
     * @param email his old email
     * @param user the updated {@link User}
     * @return the updated {@link User}
     */
    public User updateUser(String email, User user) {
        if (userRepository.existsByEmail(email) < 1) {
            String message = String.format("Could not find user with the mail address: %s", email);
            throw new RecordNotFoundException(message);
        }
        if (!user.getEmail().equals(email)) {
            userRepository.updateUserEmail(email, user.getEmail());
        }
        userRepository.save(user);
        return user;
    }


}

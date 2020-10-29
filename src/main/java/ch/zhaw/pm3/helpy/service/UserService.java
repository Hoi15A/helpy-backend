package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.exception.RecordAlreadyExistsException;
import ch.zhaw.pm3.helpy.exception.RecordNotFoundException;
import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.JobRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JobRepository jobRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) throw new RecordNotFoundException(email);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()) > 0) {
            String message = String.format("User with the id: %s already exists", user.getEmail());
            throw new RecordAlreadyExistsException(message);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

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

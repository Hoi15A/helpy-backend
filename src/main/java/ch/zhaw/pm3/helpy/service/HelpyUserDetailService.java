package ch.zhaw.pm3.helpy.service;

import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HelpyUserDetailService implements UserDetailsService  {
    private final UserRepository userRepository;

    public HelpyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> helpyUser = userRepository.findById(username);
        if (helpyUser.isEmpty()) {
            String message = String.format("Username %s not found", username);
            throw new UsernameNotFoundException(message);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(helpyUser.get().getEmail())
                .password(helpyUser.get().getPassword())
                .authorities("USER").build();
    }
}

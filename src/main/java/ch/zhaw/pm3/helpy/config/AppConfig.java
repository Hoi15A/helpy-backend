package ch.zhaw.pm3.helpy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class to set basic configuration of App
 */
@Configuration
public class AppConfig {

    /**
     * Gets the {@link PasswordEncoder} to be used on login and register.
     * @return the {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

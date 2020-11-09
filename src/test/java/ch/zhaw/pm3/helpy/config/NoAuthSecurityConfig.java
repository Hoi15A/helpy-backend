package ch.zhaw.pm3.helpy.config;

import ch.zhaw.pm3.helpy.constant.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile(Profiles.NO_AUTH)
public class NoAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF
        http.csrf().disable()
                // Permit all requests without authentication
                .authorizeRequests().anyRequest().permitAll();
    }

}

package ch.zhaw.pm3.helpy.test.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.LocationStrategy;
import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.repository.LocationRepository;
import ch.zhaw.pm3.helpy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LocationMatcherTest {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Mock
    Job job;

    @Mock
    User user;

    LocationStrategy locationStrategy = new LocationStrategy();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        locationStrategy.setLocationRepository(locationRepository);
        locationStrategy.setUserRepository(userRepository);
        when(job.getAuthor()).thenReturn(user);
        when(job.getAuthor().getPlz()).thenReturn(8400);
    }

    @Test
    void contextLoads() {
        assertNotNull(locationRepository);
    }

    @Test
    void testLocation() {
        assertEquals(7, locationStrategy.filterPotentialHelpers(job, new ArrayList<>(userRepository.findAllByRating())).size());
        when(job.getAuthor().getPlz()).thenReturn(1723);
        assertEquals(3, locationStrategy.filterPotentialHelpers(job, new ArrayList<>(userRepository.findAllByRating())).size());
    }

}

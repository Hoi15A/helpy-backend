package ch.zhaw.pm3.helpy.test.matcher.filter;

import ch.zhaw.pm3.helpy.matcher.filter.LocationFilter;
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
class LocationFilterTest {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Mock
    Job job;

    @Mock
    User user;

    final LocationFilter locationFilter = new LocationFilter();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        locationFilter.setLocationRepository(locationRepository);
        locationFilter.setUserRepository(userRepository);
        when(job.getAuthor()).thenReturn(user);
        when(job.getAuthor().getPlz()).thenReturn(8400);
    }

    @Test
    void contextLoads() {
        assertNotNull(locationRepository);
    }

    @Test
    void testLocation() {
        assertEquals(7, locationFilter.filterPotentialHelpers(job, new ArrayList<>(userRepository.findAllByRating())).size());
        when(job.getAuthor().getPlz()).thenReturn(1723);
        assertEquals(3, locationFilter.filterPotentialHelpers(job, new ArrayList<>(userRepository.findAllByRating())).size());
    }

}

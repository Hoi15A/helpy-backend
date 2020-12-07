package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for the {@link Location} entity.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Query to get a location from the given postcode
     * @param plz the postcode
     * @return a {@link Location} or null.
     */
    @Query("select l from Location l where l.plz = ?1")
    Location getLocationByPlz(int plz);
}

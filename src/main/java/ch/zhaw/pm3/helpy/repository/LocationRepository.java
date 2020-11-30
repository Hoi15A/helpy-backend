package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select l from Location l where l.plz = ?1")
    Location getLocationByPlz(int plz);
}

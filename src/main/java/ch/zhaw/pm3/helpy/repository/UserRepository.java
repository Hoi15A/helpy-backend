package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.Helpseeker;
import ch.zhaw.pm3.helpy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from User where email=?1", nativeQuery = true)
    User findUserByName(String username);

    @Query(value = "select * from User where plz=?1", nativeQuery = true)
    List<User> findUsersByPlz(int plz);

    @Query(value = "select * from User where status=?1", nativeQuery = true)
    List<User> findUsersByStatus(String status);

    @Query(value = "select * from User where ratings=?1 and dtype='Helper'", nativeQuery = true)
    List<User> findUsersByRating(int rating);

    @Query(value = "select * from User where email=?1 and dtype='Helpseeker'", nativeQuery = true)
    Helpseeker findHelpseekerByName(String name);
}

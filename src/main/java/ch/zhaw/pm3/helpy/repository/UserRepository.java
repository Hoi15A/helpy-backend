package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email=?1")
    User findUserByName(String username);

    @Query("select u from User u where u.plz=?1")
    List<User> findUsersByPlz(int plz);

    @Query("select u from User u where u.status=?1")
    List<User> findUsersByStatus(String status);

    @Query("select h from Helper h where h.ratings=?1")
    List<Helper> findUsersByRating(int rating);

    @Query("select h from Helpseeker h where h.email=?1")
    Helpseeker findHelpseekerByName(String name);
}

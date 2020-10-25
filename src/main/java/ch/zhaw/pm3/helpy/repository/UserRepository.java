package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     *
     * @param username
     * @return
     */
    @Query("select u from User u where u.email=?1")
    User findUserByName(String username);

    /**
     *
     * @param plz
     * @return
     */
    @Query("select u from User u where u.plz=?1")
    List<User> findUsersByPlz(int plz);

    /**
     *
     * @param status
     * @return
     */
    @Query("select u from User u where u.status=?1")
    List<User> findUsersByStatus(String status);

    /**
     *
     * @param rating
     * @return
     */
    @Query("select h from Helper h where h.ratings=?1")
    List<Helper> findUsersByRating(int rating);

    /**
     *
     * @param name
     * @return
     */
    @Query("select h from Helpseeker h where h.email=?1")
    Helpseeker findHelpseekerByName(String name);

    /**
     *
     * @param name
     * @return
     */
    @Query("select h from Helper h where h.email = ?1")
    Helper findHelperByName(String name);
}

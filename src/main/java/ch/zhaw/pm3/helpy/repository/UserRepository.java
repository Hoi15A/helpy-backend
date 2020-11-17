package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Query to get a set of Users from the database whose plz attribute is set to the given int
     * @param plz to match with a User's date attribute
     * @return set of Users with given plz
     */
    @Query("select u from User u where u.plz=?1")
    Set<User> findUsersByPlz(int plz);

    /**
     * Query to get a set of Users from the database whose status attribute is set to the given String
     * @param status to match with a User's status attribute
     * @return set of Users with given status
     */
    @Query("select u from User u where u.status=?1")
    Set<User> findUsersByStatus(String status);

    /**
     * Query to get a set of Users from the database whose rating attribute is set to the given int
     * @param rating to match with a User's rating attribute
     * @return set of Users with given rating
     */
    @Query("select u from User u where u.ratings=?1")
    Set<User> findUsersByRating(int rating);

    /**
     * Query to update the email of a user (id)
     * @param oldMail old id
     * @param newMail new id
     */
    @Modifying
    @Query("update User u set u.email=?2 where u.email=?1")
    void updateUserEmail(String oldMail, String newMail);

    /**
     * Query to check if a user exists
     * @param email id
     * @return number of existing entries (should be 0 or 1)
     */
    @Query("select count(u.email) from User u where u.email=?1")
    long existsByEmail(String email);
}

package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.user.Helper;
import ch.zhaw.pm3.helpy.model.user.Helpseeker;
import ch.zhaw.pm3.helpy.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Query to get a list of Users from the database whose plz attribute is set to the given int
     * @param plz to match with a User's date attribute
     * @return list of Users with given plz
     */
    @Query("select u from User u where u.plz=?1")
    List<User> findUsersByPlz(int plz);

    /**
     * Query to get a list of Users from the database whose status attribute is set to the given String
     * @param status to match with a User's status attribute
     * @return list of Users with given status
     */
    @Query("select u from User u where u.status=?1")
    List<User> findUsersByStatus(String status);

    /**
     * Query to get a list of Users from the database whose rating attribute is set to the given int
     * @param rating to match with a User's rating attribute
     * @return list of Users with given rating
     */
    @Query("select h from Helper h where h.ratings=?1")
    List<Helper> findUsersByRating(int rating);

    /**
     * Query to get a HelpSeeker from the database whose eMail attribute is set to the given String
     * @param email of HelpSeeker to get
     * @return HelpSeeker with given eMail
     */
    @Query("select h from Helpseeker h where h.email=?1")
    Helpseeker findHelpseekerByEmail(String email);

    /**
     * Query to get a Helper from the database whose eMail attribute is set to the given String
     * @param email of Helper to get
     * @return Helper with given eMail
     */
    @Query("select h from Helper h where h.email = ?1")
    Helper findHelperByEmail(String email);

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

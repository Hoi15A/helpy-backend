package ch.zhaw.pm3.helpy.repository;

import ch.zhaw.pm3.helpy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from User as u where u.email=?1", nativeQuery = true)
    User findUserByName(String username);
}

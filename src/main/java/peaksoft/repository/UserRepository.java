package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
    void deleteUserByEmail(String email);

    Optional<User>getUserByEmail(String email);

    Optional<User> getUsersByEmail(String email);
}

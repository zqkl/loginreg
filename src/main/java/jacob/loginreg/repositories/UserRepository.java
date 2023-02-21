package jacob.loginreg.repositories;

import java.util.Optional;

import jacob.loginreg.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);

}

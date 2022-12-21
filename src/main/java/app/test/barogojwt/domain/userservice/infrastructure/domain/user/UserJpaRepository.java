package app.test.barogojwt.domain.userservice.infrastructure.domain.user;

import app.test.barogojwt.domain.userservice.domain.user.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findByNickname(String nickname);

}

package app.test.barogojwt.domain.userservice.domain.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserRepository {

    User save(User user);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByNickname(String nickname);
}





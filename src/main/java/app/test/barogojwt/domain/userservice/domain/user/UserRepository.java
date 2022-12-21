package app.test.barogojwt.domain.userservice.domain.user;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> getUserByUserId(String userId);

    Optional<User> getUserByNickname(String nickname);
}





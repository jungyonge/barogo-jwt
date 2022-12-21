package app.test.barogojwt.domain.userservice.infrastructure.domain.user;

import app.test.barogojwt.domain.userservice.domain.user.User;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userJpaRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> getUserByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname);
    }

}

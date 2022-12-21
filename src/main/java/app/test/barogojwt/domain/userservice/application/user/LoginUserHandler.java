package app.test.barogojwt.domain.userservice.application.user;

import app.test.barogojwt.domain.userservice.domain.UserDomainValidationMessage;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginUserHandler {

    private final UserRepository userRepository;

    public LoginUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void loginUser(String username){
        userRepository.getUserByUsername(username).map(user -> {
            user.login();
            userRepository.save(user);
            return true;
        }).orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND));
    }
}

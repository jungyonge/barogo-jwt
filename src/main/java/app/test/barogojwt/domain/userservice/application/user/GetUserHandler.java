package app.test.barogojwt.domain.userservice.application.user;

import app.test.barogojwt.domain.userservice.domain.UserDomainValidationMessage;
import app.test.barogojwt.domain.userservice.domain.user.User;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUserHandler {
    private final UserRepository userRepository;

    public GetUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public User getUserByUsername(long id , String username){

        return userRepository.getUserByIdAndUsername(id, username).
                orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND));
    }
}

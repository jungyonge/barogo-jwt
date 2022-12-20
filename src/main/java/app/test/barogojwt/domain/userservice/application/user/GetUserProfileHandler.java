package app.test.barogojwt.domain.userservice.application.user;


import app.test.barogojwt.domain.userservice.domain.UserDomainValidationMessage;
import app.test.barogojwt.domain.userservice.domain.user.User;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserProfileHandler {

    private final UserRepository userRepository;



    public User getUserByNickname(String nickname) {
        return userRepository.getUserByNickname(nickname)
                .orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND));
    }


}

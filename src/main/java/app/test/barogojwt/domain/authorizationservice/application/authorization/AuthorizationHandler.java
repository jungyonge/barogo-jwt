package app.test.barogojwt.domain.authorizationservice.application.authorization;

import app.test.barogojwt.api.authorization.response.TokenDto;
import app.test.barogojwt.config.jwt.TokenProvider;
import app.test.barogojwt.domain.userservice.domain.UserDomainValidationMessage;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorizationHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthorizationHandler(UserRepository userRepository, PasswordEncoder passwordEncoder,
            TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    @Transactional
    public TokenDto loginUser(String username, String password){
        return userRepository.getUserByUsername(username).map(user -> {
            String accessToken = "";
            String refreshToken = "";
            if(passwordEncoder.matches(password, user.getPassword())){
                user.login();
                userRepository.save(user);

                accessToken = tokenProvider.createAccessToken(user.getUsername(), user.getRoles().stream()
                        .map(role -> role.getName().getValue())
                        .collect(Collectors.joining(",")));
                refreshToken = tokenProvider.createRefreshToken(user.getUsername(), user.getRoles().stream()
                        .map(role -> role.getName().getValue())
                        .collect(Collectors.joining(",")));
            }else {
                throw new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND_OR_PASSWORD_WRONG);
            }
            return new TokenDto(accessToken,refreshToken);
        }).orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND_OR_PASSWORD_WRONG));
    }
}

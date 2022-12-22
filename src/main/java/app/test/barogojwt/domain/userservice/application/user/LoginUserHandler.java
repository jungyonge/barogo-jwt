package app.test.barogojwt.domain.userservice.application.user;

import app.test.barogojwt.config.jwt.TokenProvider;
import app.test.barogojwt.domain.userservice.domain.UserDomainValidationMessage;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginUserHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginUserHandler(UserRepository userRepository, PasswordEncoder passwordEncoder,
            TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    @Transactional
    public String loginUser(String username, String password){
        return userRepository.getUserByUsername(username).map(user -> {
            String jwt = "";
            if(passwordEncoder.matches(password, user.getPassword())){
                user.login();
                userRepository.save(user);

                jwt =  tokenProvider.createToken(user.getUsername(), user.getRoles().stream()
                        .map(role -> role.getName().getValue())
                        .collect(Collectors.joining(",")));
            }else {
                throw new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND_OR_PASSWORD_WRONG)
            }
            return jwt;
        }).orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND_OR_PASSWORD_WRONG));
    }
}

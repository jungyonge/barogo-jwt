package app.test.barogojwt.domain.userservice.application.user;


import app.test.barogojwt.domain.userservice.domain.UserDomainValidationMessage;
import app.test.barogojwt.domain.userservice.domain.security.RoleName;
import app.test.barogojwt.domain.userservice.domain.security.RoleRepository;
import app.test.barogojwt.domain.userservice.domain.user.User;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserHandler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserHandler(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(String userId,  String password, String nickname) {
        var user = User.create(userId, passwordEncoder.encode(password), nickname);

        if (userRepository.getUserByUsername(userId).isPresent()) {
            throw new DomainValidationException(UserDomainValidationMessage.USER_ALREADY_EXIST);
        }

        if (userRepository.getUserByNickname(nickname).isPresent()) {
            throw new DomainValidationException(UserDomainValidationMessage.NICKNAME_ALREADY_EXIST);
        }

        var normalUser = roleRepository.getRoleByName(RoleName.ROLE_NORMAL_USER)
                .orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.ROLE_DOES_NOT_EXIST));
        user.addRole(normalUser);

        userRepository.save(user);

        return true;
    }
}
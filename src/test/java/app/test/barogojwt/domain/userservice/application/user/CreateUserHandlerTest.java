package app.test.barogojwt.domain.userservice.application.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import app.test.barogojwt.api.user.request.SignupRequest;
import app.test.barogojwt.domain.userservice.domain.security.Role;
import app.test.barogojwt.domain.userservice.domain.security.RoleName;
import app.test.barogojwt.domain.userservice.domain.security.RoleRepository;
import app.test.barogojwt.domain.userservice.domain.user.User;
import app.test.barogojwt.domain.userservice.domain.user.UserRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CreateUserHandlerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CreateUserHandler createUserHandler;


    @DisplayName("회원 가입")
    @Test
    void signUp() {
        // given
        SignupRequest request = new SignupRequest("jungyong","wjddydyd1!!23213123","jungyong","SHOP_OWNER");

        doReturn(User.create(request.getUsername(), request.getPassword(), request.getNickname(), request.getUsertype())).when(userRepository)
                .save(any(User.class));
        doReturn(Optional.of(new Role())).when(roleRepository)
                .getRoleByName(any(RoleName.class));

        // when
        User result = createUserHandler.createUser(request.getUsername(), request.getPassword(), request.getNickname(), request.getUsertype());

        // then
        assertThat(result.getUsername(),is(result.getUsername()));

        // verify
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("비밀번호 로직 확인")
    @Test
    void checkPassword() {
        // given
        SignupRequest request = new SignupRequest("jungyong","wjd123","jungyong","SHOP_OWNER");

        // when
        DomainValidationException e = assertThrows(DomainValidationException.class, () -> createUserHandler.createUser(request.getUsername(), request.getPassword(), request.getNickname(), request.getUsertype()));

        // then
        assertThat(e.getMessage(),is(equalTo("비밀번호 조건이 올바르지 않습니다.")));

    }
}
package app.test.barogojwt.domain.userservice.application.user;

import static org.junit.jupiter.api.Assertions.assertThrows;

import app.test.barogojwt.support.domain.DomainValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CreateUserHandlerTest {

    @Autowired
    private CreateUserHandler createUserHandler;

    private GetUserHandler getUserHandler;


    @DisplayName("회원가입이 정상적으로 된다")
    @Transactional
    @Test
    public void signup() {
        boolean result = createUserHandler.createUser("jungyong", "jung!112233yong", "jungyong", "SHOP_OWNER");

    }

    @DisplayName("중복된 ID 회원가입 실패")
    @Transactional
    @Test
    public void duple(){

        DomainValidationException e = assertThrows(DomainValidationException.class, () -> createUserHandler.createUser("admin", "admin", "jungyong", "SHOP_OWNER"));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 가입된 아이디 입니다.");
    }


    @DisplayName("비밀번호 로직 체크")
    @Transactional
    @Test
    public void passwordCheck(){

        DomainValidationException e = assertThrows(DomainValidationException.class, () -> createUserHandler.createUser("admin123", "admin", "admin123", "SHOP_OWNER"));
        Assertions.assertThat(e.getMessage()).isEqualTo("비밀번호 조건이 올바르지 않습니다.");
    }

}
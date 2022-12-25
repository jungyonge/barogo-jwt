package app.test.barogojwt.domain.userservice.domain;


import app.test.barogojwt.support.domain.ExplainableMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserDomainValidationMessage implements ExplainableMessage {

    USER_ID_ALREADY_EXIST(2_0001, "이미 가입된 아이디 입니다."),
    INVALID_PASSWORD(2_0002, "비밀번호 조건이 올바르지 않습니다."),
    INVALID_NICKNAME(2_0003, "올바르지 않은 닉네임입니다."),
    ROLE_ALREADY_EXIST(2_0004, "이미 존재하는 권한입니다."),
    NICKNAME_ALREADY_EXIST(2_0005, "이미 존재하는 닉네임입니다."),
    ROLE_DOES_NOT_EXIST(2_0006, "권한이 존재하지 않습니다."),
    USER_NOT_FOUND(2_0006, "사용자가 존재하지 않습니다."),
    USER_NOT_FOUND_OR_PASSWORD_WRONG(2_0007, "사용자가 존재하지 않거나 비밀번호가 틀렸습니다."),

    ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    UserDomainValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public int getStatus() {
        return status.value();
    }
}

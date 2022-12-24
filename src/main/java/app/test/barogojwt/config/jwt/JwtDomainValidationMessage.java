package app.test.barogojwt.config.jwt;

import app.test.barogojwt.support.domain.ExplainableMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum JwtDomainValidationMessage implements ExplainableMessage {

    WRONG_JWT_TOKEN(1_0001, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(1_0002, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(1_0003, "지원되지 않는 JWT 토큰입니다."),
    WRONG_TYPE_JWT_TOKEN(1_0004, "JWT 토큰이 잘못되었습니다."),
    UNKNOWN_JWT_TOKEN(1_0005,"유효한 JWT 토큰이 없습니다"),

    EXPIRED_JWT_REFRESH_TOKEN(1_0006, "만료된 JWT refresh token 토큰입니다. 로그인을 다시 해주세요.")

            ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    JwtDomainValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public int getStatus() {
        return status.value();
    }
}

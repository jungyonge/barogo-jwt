package app.test.barogojwt.domain.userservice.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    JOINED("JOINED", "가입완료"),
    USER_WITHDRAWAL("USER_WITHDRAWAL", "유저 탈퇴"),
    ADMIN_PAUSE("ADMIN_PAUSE", "관리자 정지"),
    ;

    private final String value;
    private final String desc;
}

package app.test.barogojwt.domain.userservice.domain.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleName {

    ROLE_NORMAL_USER("ROLE_NORMAL_USER", "일반 유저"),
    ROLE_ADMIN("ROLE_ADMIN", "어드민"),
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", "관리자 어드민"),
    ;

    private final String value;
    private final String desc;
}

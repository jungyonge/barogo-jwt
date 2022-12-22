package app.test.barogojwt.domain.userservice.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    ADMIN("ADMIN", "어드민"),
    COURIER("COURIER", "배달원"),
    SHOP_OWNER("SHOP_OWNER", "가게 운영자"),
    ;

    private final String value;
    private final String desc;
}
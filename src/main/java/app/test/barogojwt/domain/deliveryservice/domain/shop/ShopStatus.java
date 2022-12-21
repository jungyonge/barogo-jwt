package app.test.barogojwt.domain.deliveryservice.domain.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopStatus {

    SHOP_OPENED("SHOP_OPENED", "상점 영업"),
    SHOP_CLOSED("SHOP_CLOSED", "상점 마감"),
    SHOP_PAUSE("SHOP_PAUSE", "상점 일시정지"),


    ;

    private final String value;
    private final String desc;
}

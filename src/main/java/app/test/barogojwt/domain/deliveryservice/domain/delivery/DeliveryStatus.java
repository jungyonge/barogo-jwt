package app.test.barogojwt.domain.deliveryservice.domain.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

    ORDER_READY("READY", "준비,결제전"),
    ORDER_PAYED("ORDER_PAYED", "결제 완료"),

    ORDER_CANCELED("ORDER_CANCELED", "고객 주문 취소"),

    SHOP_CANCELED("SHOP_CANCELED", "가게 주문 취소"),

    COURIER_QUEUEING("COURIER_QUEUEING", "배달원 배차 대기"),
    COURIER_ASSIGNED("COURIER_ASSIGNED", "배달원 배차 완료"),
    COURIER_ASSIGNED_CANCELED("COURIER_ASSIGNED_CANCELED", "배달원 배차 취소"),
    COURIER_PICKED("COURIER_PICKED", "배달원 픽업 완료"),
    COURIER_DELIVERED("COURIER_DELIVERED", "배달원 배달 완료"),

    ;

    private final String value;
    private final String desc;
}

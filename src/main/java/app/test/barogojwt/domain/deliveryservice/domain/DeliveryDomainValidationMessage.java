package app.test.barogojwt.domain.deliveryservice.domain;


import app.test.barogojwt.support.domain.ExplainableMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DeliveryDomainValidationMessage implements ExplainableMessage {

    INVALID_SEARCH_DATE(1_0001, "3일 이상은 조회가 불가능 합니다."),
    INVALID_CHANGE_DELIVERY_ADDRESS(1_0002, "배달원 배차 전에만 주소변경이 가능합니다."),


    ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    DeliveryDomainValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public int getStatus() {
        return status.value();
    }
}

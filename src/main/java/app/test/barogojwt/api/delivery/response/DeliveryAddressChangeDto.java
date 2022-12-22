package app.test.barogojwt.api.delivery.response;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddressChangeDto {

    private long id;

    private String deliveryAddress;

    private long shopId;

}

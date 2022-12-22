package app.test.barogojwt.api.delivery.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddressChangeRequest {

    private long id;

    private String newDeliveryAddress;


}

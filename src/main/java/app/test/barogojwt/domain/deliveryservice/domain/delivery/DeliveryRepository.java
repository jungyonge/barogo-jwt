package app.test.barogojwt.domain.deliveryservice.domain.delivery;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);

    List<Delivery> getDeliveryBySpec(long userId, long shopId, String status , LocalDateTime searchStartDate, LocalDateTime searchEndDate);

}

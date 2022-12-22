package app.test.barogojwt.domain.deliveryservice.domain.delivery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);

    Optional<Delivery> getDeliveryById(long id);

    List<Delivery> getDeliveryBySpec(long userId, long shopId, String status , LocalDate searchStartDate, LocalDate searchEndDate);

}

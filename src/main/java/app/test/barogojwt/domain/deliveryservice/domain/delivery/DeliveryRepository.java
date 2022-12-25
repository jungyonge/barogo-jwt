package app.test.barogojwt.domain.deliveryservice.domain.delivery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);

    Optional<Delivery> getDeliveryByIdAndShopId(long id, long shopId);



    List<Delivery> getDeliveryBySpec(long userId, long shopId, String status , LocalDate searchStartDate, LocalDate searchEndDate);

}

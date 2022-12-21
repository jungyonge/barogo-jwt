package app.test.barogojwt.domain.deliveryservice.domain.delivery;

import java.util.Optional;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);

    Optional<Delivery> getDeliveryById(long id);

}

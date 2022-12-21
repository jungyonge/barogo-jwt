package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.userservice.domain.user.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryJpaRepository extends CrudRepository<Delivery, Long> {

    Optional<Delivery> findDeliveryById(long id);

}

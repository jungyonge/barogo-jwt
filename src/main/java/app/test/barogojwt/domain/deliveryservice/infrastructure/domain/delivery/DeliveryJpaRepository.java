package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.userservice.domain.user.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryJpaRepository extends CrudRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery> {

    Optional<Delivery> findDeliveryById(long id);

    List<Delivery> findAll(Specification<Delivery> spec);

}

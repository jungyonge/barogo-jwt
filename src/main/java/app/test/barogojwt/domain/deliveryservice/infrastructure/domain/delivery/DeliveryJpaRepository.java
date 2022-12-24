package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryJpaRepository extends CrudRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery> {

    Optional<Delivery> findDeliveryByIdAndShopId(long id, long shopId);

    List<Delivery> findAll(Specification<Delivery> spec);



}

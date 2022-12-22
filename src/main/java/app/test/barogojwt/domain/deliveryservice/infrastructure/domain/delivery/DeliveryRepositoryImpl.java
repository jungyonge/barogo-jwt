package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryRepository;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;


    @Override
    public Delivery save(Delivery delivery) {
        return deliveryJpaRepository.save(delivery);
    }

    @Override
    public List<Delivery> getDeliveryBySpec(long userId, long shopId,
            String status ,LocalDateTime searchStartDate, LocalDateTime searchEndDate) {

        Specification<Delivery> spec = Specification.where(DeliverySpecification.equalParam("userId", userId));

        if(shopId != 0){
            spec = spec.and(DeliverySpecification.equalParam("shopId", shopId));
        }

        if(status != null){
            spec = spec.and(DeliverySpecification.equalParam("status", status));
        }

        if(searchStartDate != null && searchEndDate != null) {
            spec = spec.and(DeliverySpecification.betweenCreatedDatetime(searchStartDate, searchEndDate));
        }

        return deliveryJpaRepository.findAll(spec);
    }


}

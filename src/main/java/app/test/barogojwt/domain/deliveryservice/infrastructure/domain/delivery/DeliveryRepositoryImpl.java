package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    public Optional<Delivery> getDeliveryByIdAndShopId(long id, long shopId) {
        return deliveryJpaRepository.findDeliveryByIdAndShopId(id, shopId);
    }

    @Override
    public List<Delivery> getDeliveryBySpec(long userId, long shopId,
            String status ,LocalDate searchStartDate, LocalDate searchEndDate) {

        Specification<Delivery> spec = Specification.where(DeliverySpecification.equalParam("userId", userId));

        if(shopId != 0){
            spec = spec.and(DeliverySpecification.equalParam("shopId", shopId));
        }

        if(status != null){
            spec = spec.and(DeliverySpecification.equalParam("status", status));
        }

        if(searchStartDate != null && searchEndDate != null) {

            spec = spec.and(DeliverySpecification.betweenCreatedDatetime(searchStartDate.atStartOfDay(), searchEndDate.atTime(
                    LocalTime.MAX)));
        }

        return deliveryJpaRepository.findAll(spec);
    }


}

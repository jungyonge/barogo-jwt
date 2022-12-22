package app.test.barogojwt.domain.deliveryservice.application.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryRepository;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetDeliveryHandler {

    private final DeliveryRepository deliveryRepository;

    public GetDeliveryHandler(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> getDelivery(long userId, long shopId,
            String status ,LocalDateTime searchStartDate, LocalDateTime searchEndDate){
        return deliveryRepository.getDeliveryBySpec(userId, shopId, status, searchStartDate, searchEndDate);
    }
}

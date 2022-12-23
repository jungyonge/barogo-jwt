package app.test.barogojwt.domain.deliveryservice.application.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.DeliveryDomainValidationMessage;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryRepository;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryStatus;
import app.test.barogojwt.support.domain.DomainValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetDeliveryHandler {

    private final DeliveryRepository deliveryRepository;

    public GetDeliveryHandler(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional(readOnly = true)
    public List<Delivery> getDelivery(long userId, long shopId,
            String status , LocalDate searchStartDate, LocalDate searchEndDate){

        Period period = Period.between(searchStartDate, searchEndDate);

        if(period.getDays() > 3){
            throw new DomainValidationException(DeliveryDomainValidationMessage.INVALID_SEARCH_DATE);
        }

        return deliveryRepository.getDeliveryBySpec(userId, shopId, status, searchStartDate, searchEndDate);
    }
}

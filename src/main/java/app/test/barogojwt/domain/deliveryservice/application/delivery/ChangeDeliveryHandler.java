package app.test.barogojwt.domain.deliveryservice.application.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.DeliveryDomainValidationMessage;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryRepository;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryStatus;
import app.test.barogojwt.support.domain.DomainValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeDeliveryHandler {

    private final DeliveryRepository deliveryRepository;

    public ChangeDeliveryHandler(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional
    public Delivery changeDeliveryAddress(long id, String deliveryAddress) {
        return deliveryRepository.getDeliveryById(id).map(delivery -> {
            if (DeliveryStatus.COURIER_QUEUEING.equals(delivery.getStatus())
                    || DeliveryStatus.ORDER_PAYED.equals(delivery.getStatus())
                    || DeliveryStatus.ORDER_READY.equals(delivery.getStatus())) {
                delivery.changeDeliveryAddress(deliveryAddress);
                deliveryRepository.save(delivery);
            } else {
                throw new DomainValidationException(DeliveryDomainValidationMessage.INVALID_CHANGE_DELIVERY_ADDRESS);
            }
            return delivery;
        }).orElseThrow(() -> new DomainValidationException(DeliveryDomainValidationMessage.INVALID_CHANGE_DELIVERY_ADDRESS));
    }
}

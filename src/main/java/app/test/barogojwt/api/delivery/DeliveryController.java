package app.test.barogojwt.api.delivery;

import app.test.barogojwt.api.delivery.request.DeliverySearchRequest;
import app.test.barogojwt.config.security.CustomUserDetails;
import app.test.barogojwt.domain.deliveryservice.application.delivery.GetDeliveryHandler;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final GetDeliveryHandler getDeliveryHandler;

    public DeliveryController(GetDeliveryHandler getDeliveryHandler) {
        this.getDeliveryHandler = getDeliveryHandler;
    }

    @GetMapping
    @Secured({"ROLE_NORMAL_USER"})
    public String getDelivery(CustomUserDetails customUserDetails,
            @Valid @RequestBody DeliverySearchRequest deliveryRequest) {

        var list = getDeliveryHandler.getDelivery(customUserDetails.getId(),
                deliveryRequest.getShopId(), deliveryRequest.getDeliveryStatus(),
                deliveryRequest.getSearchStartDateTime(), deliveryRequest.getSearchEndDateTime());

        return "OK";
    }

    @PutMapping("/address")
    @Secured({"ROLE_NORMAL_USER"})
    public String modifyAddress() {
        return "OK";
    }

}

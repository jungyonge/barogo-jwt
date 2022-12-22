package app.test.barogojwt.api.delivery;

import app.test.barogojwt.api.delivery.request.DeliveryAddressChangeRequest;
import app.test.barogojwt.api.delivery.request.DeliverySearchRequest;
import app.test.barogojwt.api.delivery.response.DeliveryAddressChangeDto;
import app.test.barogojwt.api.delivery.response.DeliveryDto;
import app.test.barogojwt.config.security.CustomUserDetails;
import app.test.barogojwt.domain.deliveryservice.application.delivery.ChangeDeliveryHandler;
import app.test.barogojwt.domain.deliveryservice.application.delivery.GetDeliveryHandler;
import java.lang.reflect.Type;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final GetDeliveryHandler getDeliveryHandler;

    private final ChangeDeliveryHandler changeDeliveryHandler;

    private final ModelMapper modelMapper;


    public DeliveryController(GetDeliveryHandler getDeliveryHandler,
            ChangeDeliveryHandler changeDeliveryHandler, ModelMapper modelMapper) {
        this.getDeliveryHandler = getDeliveryHandler;
        this.changeDeliveryHandler = changeDeliveryHandler;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Secured({"ROLE_NORMAL_USER"})
    public List<DeliveryDto> getDelivery(CustomUserDetails customUserDetails,
            @Valid @RequestBody DeliverySearchRequest deliveryRequest) {


        var results = getDeliveryHandler.getDelivery(customUserDetails.getId(),
                deliveryRequest.getShopId(), deliveryRequest.getDeliveryStatus(),
                deliveryRequest.getSearchStartDateTime(), deliveryRequest.getSearchEndDateTime());

        Type listType = new TypeToken<List<DeliveryDto>>() {}.getType();
        List<DeliveryDto> list = modelMapper.map(results, listType);

        return list;
    }

    @PutMapping("/address")
    @Secured({"ROLE_NORMAL_USER"})
    public ResponseEntity<DeliveryAddressChangeDto> changeAddress(CustomUserDetails customUserDetails ,@Valid @RequestBody DeliveryAddressChangeRequest deliveryAddressChangeRequest) {

        var result = changeDeliveryHandler.changeDeliveryAddress(deliveryAddressChangeRequest.getId(),
                deliveryAddressChangeRequest.getNewDeliveryAddress());
        return new ResponseEntity<>(new DeliveryAddressChangeDto(result.getId(), result.getDeliveryAddress(),
                result.getShopId()), HttpStatus.OK);
    }

}

package app.test.barogojwt.api.delivery;

import app.test.barogojwt.api.delivery.request.DeliverySearchRequest;
import app.test.barogojwt.config.security.CustomUserDetails;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    @GetMapping
    @Secured({"ROLE_NORMAL_USER"})
    public String getDelivery(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody DeliverySearchRequest deliveryRequest){

        System.out.println(customUserDetails);

        return "OK";
    }

    @PutMapping("/address")
    @Secured({"ROLE_NORMAL_USER"})
    public String modifyAddress(){
        return "OK";
    }

}

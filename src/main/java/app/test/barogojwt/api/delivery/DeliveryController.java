package app.test.barogojwt.api.delivery;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    @GetMapping
    @Secured({"ROLE_NORMAL_USER"})
    public String getDelivery(){
        return "OK";
    }

}

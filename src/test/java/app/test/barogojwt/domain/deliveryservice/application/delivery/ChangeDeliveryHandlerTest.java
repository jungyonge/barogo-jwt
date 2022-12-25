package app.test.barogojwt.domain.deliveryservice.application.delivery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryRepository;
import app.test.barogojwt.domain.deliveryservice.domain.shop.Shop;
import app.test.barogojwt.domain.deliveryservice.domain.shop.ShopRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChangeDeliveryHandlerTest {

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private ShopRepository shopRepository;
    @InjectMocks
    private ChangeDeliveryHandler changeDeliveryHandler;

    @DisplayName("배달주소 변경 성공")
    @Test
    void changeDeliveryAddress() {

        Delivery delivery = Delivery.create("서울",1, LocalDateTime.now());
        doReturn(Optional.of(new Shop())).when(shopRepository).getShopByIdAndUserId(1,1);
        doReturn(Optional.of(delivery)).when(deliveryRepository).getDeliveryByIdAndShopId(1,1);

        var result = changeDeliveryHandler.changeDeliveryAddress(1,1,1,"수원");

        assertThat(result.getDeliveryAddress(), is("수원"));
    }
}
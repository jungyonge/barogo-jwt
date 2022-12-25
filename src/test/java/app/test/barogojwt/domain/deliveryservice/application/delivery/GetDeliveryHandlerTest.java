package app.test.barogojwt.domain.deliveryservice.application.delivery;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryRepository;
import app.test.barogojwt.support.domain.DomainValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetDeliveryHandlerTest {

    @Mock
    private DeliveryRepository deliveryRepository;
    @InjectMocks
    private GetDeliveryHandler getDeliveryHandler;

    @DisplayName("배달 조회")
    @Test
    void getDelivery(){

        //given
        doReturn(getDeliveryList()).when(deliveryRepository).getDeliveryBySpec(1,1,"", LocalDate.now(), LocalDate.now());
        //wehn
        List<Delivery> list = getDeliveryHandler.getDelivery(1,1,"", LocalDate.now(), LocalDate.now());
        //then
        assertThat(list.size(), is(5));

        verify(deliveryRepository, times(1)).getDeliveryBySpec(1,1,"", LocalDate.now(), LocalDate.now());
    }

    @DisplayName("배달 조회 기간 로직 확인")
    @Test
    void getDeliveryWithException(){

        // when
        DomainValidationException e = assertThrows(DomainValidationException.class, () -> getDeliveryHandler.getDelivery(1,1,"", LocalDate.now().minusDays(4), LocalDate.now()));
        // then
        assertThat(e.getMessage(),is(equalTo("3일 이상은 조회가 불가능 합니다.")));

        // when
        e = assertThrows(DomainValidationException.class, () -> getDeliveryHandler.getDelivery(1,1,"", LocalDate.now(), LocalDate.now().minusDays(4)));
        // then
        assertThat(e.getMessage(),is(equalTo("조회종료일이 조회시작일보다 빠릅니다.")));
    }

    private List<Delivery> getDeliveryList(){
        List<Delivery> list = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i++){
            Delivery temp = new Delivery();
            list.add(temp);
        }

        return list;
    }
}
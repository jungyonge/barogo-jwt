package app.test.barogojwt.domain.deliveryservice.domain.delivery;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`delivery`")
@Getter
@NoArgsConstructor
public class Delivery {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private String deliveryAddress;

    private long shopId;

    private long userId;

    private LocalDateTime expectCookTime;

    private LocalDateTime created;

    private LocalDateTime updated;

    public Delivery(String deliveryAddress, long shopId,
            LocalDateTime expectCookTime){
        this.setDeliveryAddress(deliveryAddress);
        this.setShopId(shopId);
        this.setExpectCookTime(expectCookTime);
        this.setCreated(LocalDateTime.now());

    }

    public static Delivery create(String deliveryAddress, long shopId, LocalDateTime expectCookTime){

        return new Delivery(deliveryAddress, shopId, expectCookTime);

    }

    public void changeDeliveryAddress(String deliveryAddress){
        this.setDeliveryAddress(deliveryAddress);
    }

    private void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    private void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    private void setShopId(long shopId) {
        this.shopId = shopId;
    }

    private void setUserId(long userId) {
        this.userId = userId;
    }

    private void setExpectCookTime(LocalDateTime expectCookTime) {
        this.expectCookTime = expectCookTime;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }

    private void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}

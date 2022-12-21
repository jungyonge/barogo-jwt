package app.test.barogojwt.domain.deliveryservice.domain.shop;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.DeliveryStatus;
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
@Table(name = "`shop`")
@Getter
@NoArgsConstructor
public class Shop {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String shopName;

    private String shopAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDateTime created;

    private LocalDateTime updated;

}
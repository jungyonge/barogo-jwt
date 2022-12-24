package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.shop;

import app.test.barogojwt.domain.deliveryservice.domain.shop.Shop;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ShopJpaRepository extends CrudRepository<Shop, Long> {

    Optional<Shop> findShopById(long id);

    Optional<Shop> findShopByIdAndUserId(long id, long userId);
}

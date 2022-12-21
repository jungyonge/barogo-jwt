package app.test.barogojwt.domain.deliveryservice.domain.shop;

import java.util.Optional;

public interface ShopRepository {

    Shop save(Shop shop);

    Optional<Shop> getShopById(long id);

}
